package com.te.ems.exception;

import com.te.ems.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public record ApplicationHandlerException() {
    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handle(EmployeeNotFoundException e){
        return ResponseEntity.<ErrorResponse>ofNullable(ErrorResponse.builder()
                        .error(e.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .timestamp(LocalDateTime.now())
                .build());

    }

    @ExceptionHandler(value = {AddressTypeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handle(AddressTypeNotFoundException e){
        return ResponseEntity.<ErrorResponse>ofNullable(ErrorResponse.builder()
                .error(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build());

    }
}
