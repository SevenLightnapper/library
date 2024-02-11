package com.my.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(GenreNotFoundException.class)
  public ResponseEntity<?> handleGenreNotFoundException(GenreNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AuthorNotFoundException.class)
  public ResponseEntity<?> handleAuthorNotFoundException(AuthorNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CommentNotFoundException.class)
  public ResponseEntity<?> handleCommentNotFoundException(CommentNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameAlreadyTakenException.class)
  public ResponseEntity<?> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyTakenException.class)
  public ResponseEntity<?> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IncorrectPasswordException.class)
  public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handAccessDeniedException(AccessDeniedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

}