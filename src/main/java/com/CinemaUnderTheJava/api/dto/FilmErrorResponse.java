package com.cinemaUnderTheJava.api.dto;

import org.springframework.http.HttpStatus;

public record FilmErrorResponse (String message, HttpStatus status) {
}
