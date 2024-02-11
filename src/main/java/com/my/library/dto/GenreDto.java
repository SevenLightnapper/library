package com.my.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GenreDto {

  @NotBlank(message = "Название жанра не должно быть пустым")
  private String name;

}
