package com.my.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  private String email;

  private Set<String> roles;

  private LocalDateTime createdAt;

  private Boolean enabled;

}
