package com.my.library.controller;

import com.my.library.dto.GenreDto;
import com.my.library.model.Genre;
import com.my.library.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/genres")
public class GenreController {

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  /**
   * Метод для получения списка всех жанров
   * @apiNote GET /genres
   * @return возвращает одно из двух
   * <ul>
   *   <li>список всех жанров</li>
   *   <li>пустой список</li>
   * </ul>
   */
  @GetMapping
  public List<Genre> getAllGenres() {
    return genreService.getAllGenres();
  }

  /**
   * Метод для добавления нового жанра
   * @apiNote POST /genres
   * <p>Тело запроса должно содержать данные жанра в формате JSON</p>
   * @param genreDto жанр, который добавляется
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Genre createGenre(@Valid @RequestBody GenreDto genreDto) {
    return genreService.createGenre(genreDto);
  }

  /**
   * Метод для получения жанра по ID
   * @apiNote GET /genres/{id}
   * <p>Замените {id} на идентификатор жанра</p>
   * @param id ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>Если жанр найден, метод возвращает ResponseEntity с жанром и статус HTTP 200 (OK)</li>
   *   <li>Если жанр не найден, метод возвращает ResponseEntity с HTTP статусом 404 (Not Found)</li>
   * </ul>
   */
  @GetMapping("/{id}")
  public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
    return genreService.getGenreById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Метод для обновления существующего жанра
   * @param id ID, по которому происходит поиск
   * @param genreDto данные жанра для обновления
   * @apiNote PUT /genres/{id}
   * <p>Замените {id} на идентификатор жанра</p>
   * <p>Тело запроса должно содержать обновленные данные жанра в формате JSON</p>
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Genre updateGenreById(@PathVariable Long id, @RequestBody GenreDto genreDto) {
    return genreService.updateGenreById(id, genreDto);
  }

  /**
   * Метод для удаления жанра
   * @apiNote DELETE /genres/{id}
   * <p>Замените {id} на идентификатор жанра</p>
   * @param id ID, по которому происходит поиск
   */
   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteGenre(@PathVariable Long id) {
    genreService.deleteGenre(id);
  }

}
