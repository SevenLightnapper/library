package com.my.library.exception;

public class EmailAlreadyTakenException extends RuntimeException {

  public EmailAlreadyTakenException(String email) {
    super("Электронная почта '" + email + "' уже занята");
  }

}