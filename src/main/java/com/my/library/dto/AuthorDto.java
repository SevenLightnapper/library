package com.my.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AuthorDto {

  @NotBlank(message = "Имя автора не должно быть пустым")
  private String name;

  private String bio;

}
