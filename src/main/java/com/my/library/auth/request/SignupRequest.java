package com.my.library.auth.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignupRequest {

  private String username;

  private String password;

  private String email;

}