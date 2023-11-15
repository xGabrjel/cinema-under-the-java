package com.cinemaUnderTheJava.api.dto;

import org.springframework.http.HttpStatus;

public record ProjectionErrorResponse(String message, HttpStatus status) {
}
