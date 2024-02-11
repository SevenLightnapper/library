package com.my.library.exception;

public class UsernameAlreadyTakenException extends RuntimeException {

  public UsernameAlreadyTakenException(String username) {
    super("Имя пользователя '" + username + "' уже занято");
  }

}