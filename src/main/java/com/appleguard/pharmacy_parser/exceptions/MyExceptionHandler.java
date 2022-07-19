package com.appleguard.pharmacy_parser.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchCityException.class)
     ResponseEntity<ExceptionMessage> handleNoSuchCityException() {
        return new ResponseEntity<>(new ExceptionMessage("There is no such city"), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoSuchDrugException.class)
    ResponseEntity<ExceptionMessage> handleNoSuchDrugException() {
        return new ResponseEntity<>(new ExceptionMessage("There is no such drug"), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionMessage {
        private String message;
    }
}
