package com.bristoHQ.devHub.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public class MessageResponseDTO {
    private boolean isSuccess;
    private String message;
    private Date timestamp;
    public MessageResponseDTO() {
    }
    public MessageResponseDTO(boolean isSuccess, String message, Date timestamp) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.timestamp = timestamp;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "MessageResponseDTO [isSuccess=" + isSuccess + ", message=" + message + ", timestamp=" + timestamp + "]";
    }

    

    
}
