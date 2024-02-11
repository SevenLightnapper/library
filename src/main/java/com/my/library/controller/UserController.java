package com.my.library.controller;

import com.my.library.dto.ChangePasswordRequest;
import com.my.library.dto.UpdateEmailRequest;
import com.my.library.dto.UpdateUsernameRequest;
import com.my.library.dto.UserDto;
import com.my.library.exception.EmailAlreadyTakenException;
import com.my.library.exception.IncorrectPasswordException;
import com.my.library.exception.UserNotFoundException;
import com.my.library.exception.UsernameAlreadyTakenException;
import com.my.library.model.User;
import com.my.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  public ResponseEntity<UserDto> getUserProfile(Principal principal) {
    String username = principal.getName();
    User user = userService.getUserByUsername(username);
    UserDto userDto = convertToDto(user);
    return ResponseEntity.ok(userDto);
  }

  @PutMapping("/profile/change-password")
  public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                          Principal principal) {
    try {
      userService.changePassword(principal.getName(), changePasswordRequest);
      return ResponseEntity.ok().body("Пароль обновлен успешно");
    } catch (IncorrectPasswordException | UserNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/profile/update-email")
  public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailRequest updateEmailRequest,
                                       Principal principal) {
    try {
      userService.updateEmail(principal.getName(), updateEmailRequest.getNewEmail());
      return ResponseEntity.ok().body("Электронная почта обновлена успешно");
    } catch (EmailAlreadyTakenException | UserNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/profile/update-username")
  public ResponseEntity<?> updateUsername(@RequestBody UpdateUsernameRequest updateUsernameRequest,
                                          Principal principal) {
    try {
      userService.updateUsername(principal.getName(), updateUsernameRequest.getNewUsername());
      return ResponseEntity.ok().body("Имя пользователя обновлено успешно");
    } catch (UsernameAlreadyTakenException | UserNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  @DeleteMapping("/profile")
  public ResponseEntity<?> deleteUserAccount(Principal principal) {
    userService.deleteUser(principal.getName());
    return ResponseEntity.ok("Аккаунт пользователя удален успешно");
  }

  public static UserDto convertToDto(User user) {
    Set<String> roleNames = user.getRoles().stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        roleNames,
        user.getCreatedAt(),
        user.isEnabled()
    );
  }

}
