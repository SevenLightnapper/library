package com.my.library.controller;

import com.my.library.dto.CommentDto;
import com.my.library.model.Comment;
import com.my.library.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books/{bookId}/comments")
public class BookCommentController {

  private final CommentService commentService;

  public BookCommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  /**
   * Метод для добавления нового комментария
   * @apiNote POST /books/{bookId}/comments
   * <p>Тело запроса должно содержать данные комментария в формате JSON</p>
   * @param commentDto комментарий, который добавляется
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(value = "isAuthenticated()")
  public Comment addComment(@PathVariable Long bookId, @Valid @RequestBody CommentDto commentDto) {
    commentDto.setBookId(bookId);
    return commentService.addCommentToBook(commentDto);
  }

  /**
   * Метод для получения комментариев по ID книги
   * @apiNote GET /books/{bookId}/comments/{id}
   * <p>Замените {bookId} на идентификатор книги</p>
   * <p>Замените {id} на идентификатор комментария</p>
   * @param bookId ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>Если комментарии найдены, метод возвращает ResponseEntity со списком комментариев и статус HTTP 200 (OK)</li>
   *   <li>Если комментарии не найдены, метод возвращает ResponseEntity с HTTP статусом 404 (Not Found)</li>
   * </ul>
   */
  @GetMapping
  public ResponseEntity<List<Comment>> getCommentsByBookId(@PathVariable Long bookId) {
    List<Comment> comments = commentService.getCommentsByBookId(bookId);
    return ResponseEntity.ok(comments);
  }

  /**
   * Метод для обновления существующего комментария
   * @param commentId ID, по которому происходит поиск
   * @param commentDto данные комментария для обновления
   * @apiNote PUT /books/{bookId}/comments/{commentId}
   * <p>Замените {bookId} на идентификатор книги</p>
   * <p>Замените {commentId} на идентификатор комментария</p>
   * <p>Тело запроса должно содержать обновленные данные комментария в формате JSON</p>
   */
  @PutMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(value = "isAuthenticated()")
  public Comment updateCommentById(@PathVariable Long bookId, @PathVariable Long commentId,
                                   @Valid @RequestBody CommentDto commentDto) {
    commentDto.setBookId(bookId);
    return commentService.updateComment(commentId, commentDto);
  }

  /**
   * Метод для удаления комментария
   * @apiNote DELETE /books/{bookId}/comments/{commentId}
   * <p>Замените {bookId} на идентификатор книги</p>
   * <p>Замените {id} на идентификатор комментария</p>
   * @param bookId ID книги, по которому происходит поиск
   * @param commentId ID комментария, по которому происходит поиск
   */
  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(value = "isAuthenticated()")
  public void deleteComment(@PathVariable Long bookId, @PathVariable Long commentId) {
    commentService.deleteComment(commentId);
  }

}
