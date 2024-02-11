package com.my.library.service;

import com.my.library.dto.CommentDto;
import com.my.library.exception.BookNotFoundException;
import com.my.library.exception.CommentNotFoundException;
import com.my.library.exception.UserNotFoundException;
import com.my.library.model.Book;
import com.my.library.model.Comment;
import com.my.library.model.User;
import com.my.library.repository.BookRepository;
import com.my.library.repository.CommentRepository;
import com.my.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @Transactional
  public Comment addCommentToBook(CommentDto commentDto) {
    Book book = bookRepository.findById(commentDto.getBookId())
        .orElseThrow(() -> new BookNotFoundException(commentDto.getBookId()));
    User user = userRepository.findById(commentDto.getUserId())
        .orElseThrow(() -> new UserNotFoundException(commentDto.getUserId()));

    Comment comment = new Comment();
    comment.setText(commentDto.getText());
    comment.setBook(book);
    comment.setUser(user);
    comment.setCreatedAt(LocalDateTime.now());
    return commentRepository.save(comment);
  }

  public List<Comment> getCommentsByBookId(Long bookId) {
    return commentRepository.findByBookId(bookId);
  }

  public List<Comment> getCommentsByUserId(Long userId) {
    return commentRepository.findByUserId(userId);
  }

  @Transactional
  public Comment updateComment(Long commentId, CommentDto commentDto) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException(commentId));
    Book book = bookRepository.findById(commentDto.getBookId())
        .orElseThrow(() -> new BookNotFoundException(commentDto.getBookId()));
    User user = userRepository.findById(commentDto.getUserId())
        .orElseThrow(() -> new UserNotFoundException(commentDto.getUserId()));

    comment.setText(commentDto.getText());
    comment.setBook(book);
    comment.setUser(user);
    return commentRepository.save(comment);
  }

  @Transactional
  public void deleteComment(Long commentId) {
    if (!commentRepository.existsById(commentId))
      throw new CommentNotFoundException(commentId);

    commentRepository.deleteById(commentId);
  }

}