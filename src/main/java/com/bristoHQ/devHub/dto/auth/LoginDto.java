package com.bristoHQ.devHub.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    // it's a Data Trasfer Object for Login
    private String emailOrUsername;
    private String password;

    // "firstname" : "manish",
    // "lastname" : "kumar",
    // "role" : "ADMIN"
}
