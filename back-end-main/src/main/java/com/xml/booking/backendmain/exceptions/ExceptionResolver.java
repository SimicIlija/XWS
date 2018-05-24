package com.xml.booking.backendmain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundExp(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
