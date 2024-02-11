package com.my.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BookDto {

  @NotBlank(message = "Название книги не должно быть пустым")
  private String title;

  @NotNull(message = "ID автора не должен быть пустым")
  private Long authorId;

  @NotBlank(message = "ISBN не должен быть пустым")
  @Size(min = 10, max = 17, message = "ISBN должен быть от 10 до 13 символов")
  private String isbn;

  private String summary;

  @NotNull(message = "ID жанра не должен быть пустым")
  private Long genreId;

  @Min(value = 1900, message = "Год издания книги должен быть позже 1900 года")
  private Integer year;

  @Min(value = 0, message = "Количество страниц в книге должно быть больше 0")
  private Integer numberOfPages;

}
