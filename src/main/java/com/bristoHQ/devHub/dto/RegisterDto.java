package com.bristoHQ.devHub.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {

    // it's a Data Trasfer Object for registration
    String fullName;
    String username;
    String email;
    String password;
    String provider;
}
