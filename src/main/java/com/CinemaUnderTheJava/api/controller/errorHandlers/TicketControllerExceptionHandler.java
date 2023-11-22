package com.cinemaUnderTheJava.api.controller.errorHandlers;

import com.cinemaUnderTheJava.business.exceptions.ReservationNotAvailableException;
import com.cinemaUnderTheJava.business.exceptions.TicketNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class TicketControllerExceptionHandler {

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
