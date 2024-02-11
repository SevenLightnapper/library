package com.my.library.service;

import com.my.library.dto.BookDto;
import com.my.library.exception.AuthorNotFoundException;
import com.my.library.exception.BookNotFoundException;
import com.my.library.exception.GenreNotFoundException;
import com.my.library.model.Author;
import com.my.library.model.Book;
import com.my.library.model.Genre;
import com.my.library.repository.AuthorRepository;
import com.my.library.repository.BookRepository;
import com.my.library.repository.GenreRepository;
import com.my.library.spec.BookSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final GenreRepository genreRepository;
  private final AuthorRepository authorRepository;

  public BookService(BookRepository bookRepository,
                     GenreRepository genreRepository,
                     AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
    this.authorRepository = authorRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  @Transactional
  public Book createBook(BookDto bookDto) {
    Genre genre = genreRepository.findById(bookDto.getGenreId())
        .orElseThrow(() -> new GenreNotFoundException(bookDto.getGenreId()));
    Author author = authorRepository.findById(bookDto.getAuthorId())
        .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthorId()));

    Book book = new Book();
    return convertBookDtoToBook(bookDto, book, author, genre);
  }

  @Transactional
  public Book updateBookById(Long id, BookDto bookDto) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(id));
    Author author = authorRepository.findById(bookDto.getAuthorId())
            .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthorId()));
    Genre genre = genreRepository.findById(bookDto.getGenreId())
        .orElseThrow(() -> new GenreNotFoundException(bookDto.getGenreId()));

    return convertBookDtoToBook(bookDto, book, author, genre);
  }

  private Book convertBookDtoToBook(BookDto bookDto, Book book, Author author, Genre genre) {
    book.setTitle(bookDto.getTitle());
    book.setAuthor(author);
    book.setIsbn(bookDto.getIsbn());
    book.setSummary(bookDto.getSummary());
    book.setYear(bookDto.getYear());
    book.setNumberOfPages(bookDto.getNumberOfPages());
    book.setGenre(genre);

    return bookRepository.save(book);
  }

  @Transactional
  public void deleteBook(Long id) {
    bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException(id));
    bookRepository.deleteById(id);
  }

  public List<Book> searchBooks(String title, String authorName, String isbn,
                                String genreName, Integer year,
                                Integer minPages, Integer maxPages) {
    Specification<Book> spec = Specification.where(null);

    if (title != null && !title.isEmpty())
      spec = spec.and(BookSpecifications.hasTitle(title));
    if (authorName != null && !authorName.isEmpty())
      spec = spec.and(BookSpecifications.hasAuthorName(authorName));
    if (isbn != null && !isbn.isEmpty())
      spec = spec.and(BookSpecifications.hasIsbn(isbn));
    if (genreName != null && !genreName.isEmpty())
      spec = spec.and(BookSpecifications.hasGenreName(genreName));
    if (year != null)
      spec = spec.and(BookSpecifications.hasYear(year));
    if (minPages != null && maxPages != null)
      spec = spec.and(BookSpecifications.withPageRange(minPages, maxPages));

    return bookRepository.findAll(spec, Sort.by("title").descending());
  }

  public List<Book> getBooksByTitleOrAuthorContaining(String line) {
    Specification<Book> spec = Specification.where(null);

    if (line != null && !line.isEmpty())
      spec = spec.and(BookSpecifications.titleOrAuthorContains(line));

    return bookRepository.findAll(spec, Sort.by("title").descending());
  }

  public List<Book> getBooksByAuthorId(Long authorId) {
    return bookRepository.getBooksByAuthorId(authorId);
  }

  public List<Book> getBooksByGenreId(Long genreId) {
    return bookRepository.getBooksByGenreId(genreId);
  }

}