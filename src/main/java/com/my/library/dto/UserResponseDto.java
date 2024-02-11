package com.my.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Long id;
  private String username;
  private String email;
  private Set<String> roles;
  private LocalDateTime createdAt;
  private Boolean enabled;

}
