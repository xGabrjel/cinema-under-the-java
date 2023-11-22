package com.cinemaUnderTheJava.api.controller.errorHandlers;

import org.springframework.http.HttpStatus;

public record GenericExceptionResponse<T> (T message, HttpStatus status) {
}
