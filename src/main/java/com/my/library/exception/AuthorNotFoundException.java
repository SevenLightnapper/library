package com.my.library.exception;

public class AuthorNotFoundException extends RuntimeException {

  public AuthorNotFoundException(String message) {
    super(message);
  }
  public AuthorNotFoundException(Long authorId) {
    super("Автор не найден по id :: " + authorId);
  }

}