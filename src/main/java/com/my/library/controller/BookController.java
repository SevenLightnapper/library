package com.my.library.controller;

import com.my.library.dto.BookDto;
import com.my.library.model.Book;
import com.my.library.service.BookService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST контроллер для управления книгами.
 * <p>
 * Этот контроллер предоставляет API для выполнения различных операций CRUD
 * с книгами, а также для поиска книг по различным критериям.
 * </p>
 *
 * @author SevenLightnapper
 * @apiNote Список всех запросов реализованных в классе:
 * <li>1. Получение списка всех книг: <code>GET /books</code></li>
 * <li>2. Получение книги по ID: <code>GET /books/{id}</code></li>
 * <li>3. Добавление новой книги: <code>POST /books</code></li>
 * <li>4. Обновление книги по ID: <code>PUT /books/{id}</code></li>
 * <li>5. Удаление книги по ID: <code>DELETE /books/{id}</code></li>
 * <li>6. Универсальный поиск книг: <code>GET /books/search</code></li>
 * <li>7. Поиск книг, содержащих строку в названии: <code>GET /books/search/{title}</code></li>
 */
@RestController
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Метод для получения списка всех книг
   *
   * @return возвращает одно из двух
   * <ul>
   *   <li>список всех книг</li>
   *   <li>пустой список</li>
   * </ul>
   * @apiNote GET /books
   */
  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  /**
   * Метод для получения книги по ID
   *
   * @param id ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>Если книга найдена, метод возвращает ResponseEntity с книгой и статус HTTP 200 (OK)</li>
   *   <li>Если книга не найдена, метод возвращает ResponseEntity с HTTP статусом 404 (Not Found)</li>
   * </ul>
   * @apiNote GET /books/{id}
   * <p>Замените {id} на идентификатор книги</p>
   */
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    return bookService.getBookById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Метод для добавления новой книги
   *
   * @param bookDto книга, которая добавляется
   * @apiNote POST /books
   * <p>Тело запроса должно содержать данные книги в формате JSON</p>
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public Book createBook(@Valid @RequestBody BookDto bookDto) {
    return bookService.createBook(bookDto);
  }

  /**
   * Метод для обновления существующей книги
   *
   * @param id          ID, по которому происходит поиск
   * @param bookDto данные книги для обновления
   * @apiNote PUT /books/{id}
   * <p>Замените {id} на идентификатор книги</p>
   * <p>Тело запроса должно содержать обновленные данные книги в формате JSON</p>
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public Book updateBookById(@PathVariable(value = "id") Long id,
                                             @RequestBody BookDto bookDto) {
    return bookService.updateBookById(id, bookDto);
  }

  /**
   * Метод для удаления книги
   *
   * @param id ID, по которому происходит поиск
   * @apiNote DELETE /books/{id}
   * <p>Замените {id} на идентификатор книги</p>
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  /**
   * Метод для поиска всех книг по заданным параметрам
   *
   * @param title         название книги
   * @param authorName    автор книги
   * @param isbn          ISBN книги
   * @param genreName     жанр книги
   * @param year          год издания книги
   * @param minPages      минимально количество страниц книги
   * @param maxPages      максимальное количество страниц книги
   * @return возвращает одно из двух
   * <ul>
   *   <li>список книг, с заданными параметрами</li>
   *   <li>пустой список</li>
   * </ul>
   * @apiNote GET /books/search?author=ИмяАвтора&year=ГодИздания
   * <p>Замените ИмяАвтора и ГодИздания на соответствующие значения</p>
   */
  @GetMapping("/search")
  public List<Book> searchBooks(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String authorName,
                                @RequestParam(required = false) String isbn,
                                @RequestParam(required = false) String genreName,
                                @RequestParam(required = false) Integer year,
                                @RequestParam(required = false) Integer minPages,
                                @RequestParam(required = false) Integer maxPages) {
    return bookService.searchBooks(title, authorName, isbn, genreName,
        year, minPages, maxPages);
  }

  /**
   * Метод для поиска всех книг, название или имя автора которых содержит заданную строку
   *
   * @param title строка, по которой происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>список книг, содержащих данную фразу в своем названии или имени автора</li>
   *   <li>пустой список</li>
   * </ul>
   * @apiNote GET /books/search/{title}
   * <p>Замените {title} на строку, которая должна содержаться в названии или авторе</p>
   */
  @GetMapping("/search/{title}")
  public List<Book> getBooksByTitleOrAuthorContaining(@PathVariable String title) {
    return bookService.getBooksByTitleOrAuthorContaining(title);
  }

  /**
   * Метод для поиска всех книг по ID автора
   *
   * @param authorId ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>список книг заданного автора</li>
   *   <li>пустой список</li>
   * </ul>
   * @apiNote GET /books/author/{authorId}
   * <p>Замените {genreId} на ID автора</p>
   */
  @GetMapping("/author/{authorId}")
  public List<Book> getBooksByAuthorId(@PathVariable Long authorId) {
    return bookService.getBooksByAuthorId(authorId);
  }

  /**
   * Метод для поиска всех книг по ID жанра
   *
   * @param genreId ID, по которому происходит поиск
   * @return возвращает одно из двух
   * <ul>
   *   <li>список книг заданного жанра</li>
   *   <li>пустой список</li>
   * </ul>
   * @apiNote GET /books/genre/{genreId}
   * <p>Замените {genreId} на ID жанра</p>
   */
  @GetMapping("/genre/{genreId}")
  public List<Book> getBooksByGenreId(@PathVariable Long genreId) {
    return bookService.getBooksByGenreId(genreId);
  }

}