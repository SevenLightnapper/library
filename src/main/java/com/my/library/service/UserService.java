package com.my.library.service;

import com.my.library.dto.ChangePasswordRequest;
import com.my.library.exception.EmailAlreadyTakenException;
import com.my.library.exception.IncorrectPasswordException;
import com.my.library.exception.RoleNotFoundException;
import com.my.library.exception.UserNotFoundException;
import com.my.library.exception.UsernameAlreadyTakenException;
import com.my.library.model.ERole;
import com.my.library.model.Role;
import com.my.library.model.User;
import com.my.library.repository.RoleRepository;
import com.my.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public Optional<User> findById(Long id) {
    return Optional.ofNullable(userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id)));
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  @Transactional
  public User registerNewUser(String username, String email, String password) {
    log.info("Trying to register new user with username: {}", username);

    if (userRepository.existsByUsername(username)) {
      log.warn("Registration attempt with already taken username: {}", username);
      throw new UsernameAlreadyTakenException(username);
    }

    if (userRepository.existsByEmail(email)) {
      log.warn("Registration attempt with already taken email: {}", email);
      throw new EmailAlreadyTakenException(email);
    }

    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreatedAt(LocalDateTime.now());

    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_USER.toString()));
    Set<Role> roles = new HashSet<>();
    roles.add(userRole);
    user.setRoles(roles);

    userRepository.save(user);
    log.info("User registered successfully with username: {}", username);

    return user;
  }

  @Transactional
  public User addRoleToUser(String username, ERole roleName) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    Role role = roleRepository.findByName(roleName)
        .orElseThrow(() -> new RoleNotFoundException(roleName.toString()));

    user.getRoles().add(role);
    userRepository.save(user);

    return user;
  }

  public void toggleUserEnabled(Long userId) {
    userRepository.findById(userId).ifPresent(user -> {
      user.setEnabled(!user.isEnabled());
      userRepository.save(user);
    });
  }

  @Transactional
  public void changePassword(String currentUsername,
                             ChangePasswordRequest changePasswordRequest) {
    log.info("Attempting to change password for user: {}", currentUsername);

    // Вызов метода проверки
    User user = checkUserAccess(currentUsername, "change password");

    // Проверка старого пароля
    if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),
        user.getPassword())) {
      log.warn("Incorrect current password provided for user: {}", currentUsername);
      throw new IncorrectPasswordException("You've entered an incorrect current password");
    }

    // Установка нового пароля
    user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    userRepository.save(user);
    log.info("Password successfully changed for user: {}", currentUsername);
  }

  @Transactional
  public void updateEmail(String currentUsername, String newEmail) {
    log.info("User {} is attempting to update their email to {}", currentUsername, newEmail);

    // Вызов метода проверки
    User user = checkUserAccess(currentUsername, "update email");

    if (userRepository.existsByEmail(newEmail)) {
      log.warn("Email update attempt failed for user {}: Email {} is already in use",
          currentUsername, newEmail);
      throw new EmailAlreadyTakenException(newEmail);
    }

    user.setEmail(newEmail);
    userRepository.save(user);
    log.info("Email for user {} successfully updated to {}", currentUsername, newEmail);
  }

  @Transactional
  public void updateUsername(String currentUsername, String newUsername) {
    log.info("Attempting to update username for user: {} to new username: {}",
        currentUsername, newUsername);

    // Вызов метода проверки
    User user = checkUserAccess(currentUsername, "update username");

    if (userRepository.existsByUsername(newUsername)) {
      log.warn("Update username attempt failed: {} is already taken", newUsername);
      throw new UsernameAlreadyTakenException(newUsername);
    }

    user.setUsername(newUsername);
    userRepository.save(user);
    log.info("Username for user: {} successfully updated to {}",
        currentUsername, newUsername);
  }

  public void deleteUser(String currentUsername) {
    log.info("Attempting to delete account for user: {}", currentUsername);

    // Вызов метода проверки
    User user = checkUserAccess(currentUsername, "delete account");

    userRepository.delete(user);

    log.info("Account for user: {} successfully deleted", currentUsername);
  }

  public User checkUserAccess(String currentUsername,
                              String operation) throws AccessDeniedException {
    User user = userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new UserNotFoundException(currentUsername));

    // Проверка,
    // что текущий аутентифицированный пользователь соответствует
    // целевому пользователю операции
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String loggedInUsername = authentication.getName();

    if (!user.getUsername().equals(loggedInUsername)) {
      log.warn("Access denied for user: {}. Attempted to {} without permission.",
          loggedInUsername, operation);
      throw new AccessDeniedException("Access Denied: " +
          "You do not have permission to \"" + operation + "\"");
    }

    log.info("Access granted for user: {} to {}", loggedInUsername, operation);
    return user;
  }

  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), user.getAuthorities());
  }

}
