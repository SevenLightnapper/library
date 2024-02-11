package com.my.library.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String username) {
    super("Пользователь " + username + " не найден");
  }

  public UserNotFoundException(Long userId) {
    super("Пользователь не найден по id :: " + userId);
  }

}