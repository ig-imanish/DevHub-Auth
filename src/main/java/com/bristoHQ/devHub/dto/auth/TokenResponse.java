package com.bristoHQ.devHub.dto.auth;

import org.springframework.http.HttpStatus;

public class TokenResponse {
    private String email;
    private HttpStatus httpStatus;


    public TokenResponse() {
    }
    public TokenResponse(String email, HttpStatus httpStatus) {
        this.email = email;
        this.httpStatus = httpStatus;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    @Override
    public String toString() {
        return "TokenResponse [email=" + email + ", httpStatus=" + httpStatus + "]";
    }
}
