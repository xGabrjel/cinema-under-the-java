package com.cinemaUnderTheJava.api.controller.errorHandlers;

import com.cinemaUnderTheJava.database.util.exceptions.DuplicateFilmException;
import com.cinemaUnderTheJava.database.util.exceptions.FilmNotFoundException;
import com.cinemaUnderTheJava.database.util.exceptions.InvalidFilmCategoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@Slf4j
@ControllerAdvice
public class FilmControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFilmCategoryException.class)
    public GenericExceptionResponse<String> handleInvalidFilmCategoryException(InvalidFilmCategoryException ex) {
        final String message = String.format("InvalidFilmCategory occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmNotFoundException.class)
    public GenericExceptionResponse<String> handleFilmNotFoundException(FilmNotFoundException ex) {
        final String message = String.format("FilmNotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateFilmException.class)
    public GenericExceptionResponse<String> handleDuplicateFilmException(DuplicateFilmException ex) {
        final String message = String.format("DuplicateFilmException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.CONFLICT);
    }
}
