package com.cinemaUnderTheJava.business.exceptions;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String message) {
        super(message);
    }

    public FilmNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
