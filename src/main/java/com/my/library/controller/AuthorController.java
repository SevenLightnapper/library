package com.my.library.controller;

import com.my.library.dto.AuthorDto;
import com.my.library.model.Author;
import com.my.library.service.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  /**
   * Метод для получения списка всех автора
   * @apiNote GET /authors
   * @return возвращает одно из двух
   * <ul>
   *   <li>список всех авторов</li>
   *   <li>пустой список</li>
   * </ul>
   */
  @GetMapping
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  /**
   * Метод для добавления нового автора
   * @apiNote POST /authors
   * <p>Тело запроса должно содержать данные автора в формате JSON</p>
   * @param authorDto автор, который добавляется
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public Author createAuthor(@Valid @RequestBody AuthorDto authorDto) {
    return authorService.createAuthor(authorDto);
  }

  /**
   * Метод для получения автора по ID
   * @apiNote GET /authors/{id}
   * <p>Замените {id} на идентификатор жанра</p>
   * @param id ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>Если автор найден, метод возвращает ResponseEntity с автором и статус HTTP 200 (OK)</li>
   *   <li>Если автор не найден, метод возвращает ResponseEntity с HTTP статусом 404 (Not Found)</li>
   * </ul>
   */
  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
    return authorService.getAuthorById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Метод для обновления существующего автора
   * @param id ID, по которому происходит поиск
   * @param authorDto данные автора для обновления
   * @apiNote PUT /authors/{id}
   * <p>Замените {id} на идентификатор автора</p>
   * <p>Тело запроса должно содержать обновленные данные автора в формате JSON</p>
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public Author updateAuthorById(@PathVariable Long id, @Valid @RequestBody AuthorDto authorDto) {
    return authorService.updateAuthorById(id, authorDto);
  }

  /**
   * Метод для удаления автора
   * @apiNote DELETE /authors/{id}
   * <p>Замените {id} на идентификатор автора</p>
   * @param id ID, по которому происходит поиск
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public void deleteGenre(@PathVariable Long id) {
    authorService.deleteAuthor(id);
  }

}