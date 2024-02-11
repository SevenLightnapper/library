package com.my.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommentDto {

  @NotBlank(message = "Содержание комментария не должно быть пустым")
  private String text;

  @NotNull(message = "ID книги не должен быть пустым")
  private Long bookId;

  @NotNull(message = "ID пользователя не должен быть пустым")
  private Long userId;

}
