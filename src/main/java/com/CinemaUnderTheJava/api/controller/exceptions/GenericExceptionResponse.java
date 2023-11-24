package com.cinemaUnderTheJava.api.controller.exceptions;

import org.springframework.http.HttpStatus;

public record GenericExceptionResponse<T>(T message, HttpStatus status) {
}
