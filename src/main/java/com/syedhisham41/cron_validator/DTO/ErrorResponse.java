package com.syedhisham41.cron_validator.DTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private String message;

    private HttpStatus errorCode;

    private String path;

    private LocalDateTime timeStamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, HttpStatus errorCode, String path, LocalDateTime timeStamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

}
