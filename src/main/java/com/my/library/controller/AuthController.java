package com.my.library.controller;

import com.my.library.auth.request.AuthenticationRequest;
import com.my.library.auth.request.SignupRequest;
import com.my.library.auth.response.AuthenticationResponse;
import com.my.library.dto.UserDto;
import com.my.library.dto.UserResponseDto;
import com.my.library.exception.EmailAlreadyTakenException;
import com.my.library.exception.UsernameAlreadyTakenException;
import com.my.library.jwt.JwtTokenUtil;
import com.my.library.model.User;
import com.my.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;

  @PostMapping("/signin")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new UsernameAlreadyTakenException(signUpRequest.getUsername()));
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new EmailAlreadyTakenException(signUpRequest.getEmail()));
    }

    // создаем аккаунт нового пользователя
    User user = userService.registerNewUser(
        signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

    // конвертируем сущность пользователя в UserResponseDto
    UserResponseDto userResponseDto = convertToDto(user);

    return ResponseEntity.ok(userResponseDto);//new MessageResponse("User registered successfully!")
  }

  public static UserResponseDto convertToDto(User user) {
    Set<String> roleNames = user.getRoles().stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
    return new UserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        roleNames,
        user.getCreatedAt(),
        user.isEnabled()
    );
  }

}