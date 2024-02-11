package com.my.library.exception;

public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException(String message) {
    super(message);
  }
  public CommentNotFoundException(Long commentId) {
    super("Комментарий не найден по id :: " + commentId);
  }

}