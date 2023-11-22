package com.cinemaUnderTheJava.api.controller.errorHandlers;

import com.cinemaUnderTheJava.business.exceptions.ProjectionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ProjectionControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProjectionNotFoundException.class)
    public GenericExceptionResponse<String> handleProjectionNotFoundException(ProjectionNotFoundException ex) {
        final String message = String.format("ProjectionNotFoundException occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        return new GenericExceptionResponse<>(message, HttpStatus.NOT_FOUND);
    }
}
