package com.bristoHQ.devHub.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageResponseDTO {
    private boolean isSuccess;
    private String message;
    private Date timestamp;
}
