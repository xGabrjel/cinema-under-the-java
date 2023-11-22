package com.cinemaUnderTheJava.api.controller.errorHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public GenericExceptionResponse<String> handleException(Exception ex) {
        final String message = String.format("ProjectionNotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
