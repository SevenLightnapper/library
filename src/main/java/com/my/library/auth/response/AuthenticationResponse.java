package com.my.library.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class AuthenticationResponse {

  private final String jwt;

}