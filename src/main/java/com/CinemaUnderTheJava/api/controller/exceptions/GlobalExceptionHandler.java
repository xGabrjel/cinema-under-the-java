package com.cinemaUnderTheJava.api.controller.exceptions;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.*;
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

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProjectionNotFoundException.class)
    public GenericExceptionResponse<String> handleProjectionNotFoundException(ProjectionNotFoundException ex) {
        final String message = String.format("ProjectionNotFoundException occurred: [%s]", ex.getMessage());
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TicketNotFoundException.class)
    public GenericExceptionResponse<String> handleTicketNotFoundException(TicketNotFoundException ex) {
        final String message = String.format("ProjectionNotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TicketNotFoundException.class)
    public GenericExceptionResponse<String> handleReservationNotAvailableException(ReservationNotAvailableException ex) {
        final String message = String.format("ProjectionNotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.BAD_REQUEST);
    }

}
