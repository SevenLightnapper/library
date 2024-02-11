package com.my.library.exception;

public class RoleNotFoundException extends RuntimeException {

  public RoleNotFoundException(String role) {
    super("Роль " + role + " не найдена");
  }

}