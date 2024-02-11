package com.my.library.exception;

public class GenreNotFoundException extends RuntimeException {

  public GenreNotFoundException(String message) {
    super(message);
  }

  public GenreNotFoundException(Long id) {
    super("Жанр не найден по id ::" + id);
  }

}