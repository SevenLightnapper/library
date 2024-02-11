package com.my.library.spec;

import com.my.library.model.Author;
import com.my.library.model.Book;
import com.my.library.model.Genre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

  public static Specification<Book> hasTitle(String title) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
            "%" + title.toLowerCase() + "%");
  }

  public static Specification<Book> hasAuthorName(String authorName) {
    return (root, query, criteriaBuilder) -> {
      Join<Book, Author> authorJoin = root.join("author");
      return criteriaBuilder.like(criteriaBuilder.lower(authorJoin.get("name")),
          "%" + authorName.toLowerCase() + "%");
    };
  }

  public static Specification<Book> hasGenreName(String genreName) {
    return (root, query, criteriaBuilder) -> {
      Join<Book, Genre> genreJoin = root.join("genre");
      return criteriaBuilder.like(criteriaBuilder.lower(genreJoin.get("name")),
          "%" + genreName.toLowerCase() + "%");
    };
  }

  public static Specification<Book> hasIsbn(String isbn) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("isbn"),
        "%" + isbn + "%");
  }

  public static Specification<Book> hasYear(Integer year) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get("year"), year);
  }

  public static Specification<Book> withPageRange(Integer minPages, Integer maxPages) {
    return  (root, query, criteriaBuilder) -> {
      Predicate greaterThanOrEqualTo =
          criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfPages"), minPages);
      Predicate lessThanOrEqualTo =
          criteriaBuilder.lessThanOrEqualTo(root.get("numberOfPages"), maxPages);
      return criteriaBuilder.and(greaterThanOrEqualTo, lessThanOrEqualTo);
    };
  }

  public static Specification<Book> titleOrAuthorContains(String searchString) {
    return ((root, query, criteriaBuilder) -> {
      if (searchString == null || searchString.isEmpty())
        return criteriaBuilder.conjunction();

      String searchLower = "%" + searchString.toLowerCase() + "%";

      Join<Book, Author> authorJoin = root.join("author");

      Predicate titlePredicate = criteriaBuilder.like(
          criteriaBuilder.lower(root.get("title")), searchLower);

      Predicate authorPredicate = criteriaBuilder.like(
          criteriaBuilder.lower(authorJoin.get("name")), searchLower);

      return criteriaBuilder.or(titlePredicate, authorPredicate);
    });
  }

}