package com.cinemaUnderTheJava.api.controller.exceptions;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.AlreadyExistsException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.DuplicateException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.InvalidFilmCategoryException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import jakarta.validation.ValidationException;
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

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFilmCategoryException.class)
    public GenericExceptionResponse<String> handleInvalidFilmCategoryException(InvalidFilmCategoryException ex) {
        final String message = String.format("InvalidFilmCategory occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateException.class)
    public GenericExceptionResponse<String> handleDuplicateException(DuplicateException ex) {
        final String message = String.format("DuplicateException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public GenericExceptionResponse<String> handleNotFoundException(NotFoundException ex) {
        final String message = String.format("NotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ValidationException.class)
    public GenericExceptionResponse<String> handleValidationException(ValidationException ex) {
        final String message = String.format("ValidationException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public GenericExceptionResponse<String> handleAlreadyExistsException(AlreadyExistsException ex) {
        final String message = String.format("AlreadyExistsException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.CONFLICT);
    }
}
