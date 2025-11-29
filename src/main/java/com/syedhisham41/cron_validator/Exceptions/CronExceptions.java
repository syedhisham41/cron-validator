package com.syedhisham41.cron_validator.Exceptions;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.syedhisham41.cron_validator.DTO.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CronExceptions {

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponse> ParseExceptionHandler(ParseException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);

    }

    @ExceptionHandler(DangerousCronException.class)
    public ResponseEntity<ErrorResponse> dangerousCronHandler(DangerousCronException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }
}
