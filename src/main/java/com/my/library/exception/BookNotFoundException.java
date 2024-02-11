package com.my.library.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String message) {
    super(message);
  }

  public BookNotFoundException(Long id) {
    super("Книга не найдена по id :: " + id);
  }

}