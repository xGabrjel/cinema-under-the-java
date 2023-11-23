package com.cinemaUnderTheJava.business.exceptions;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(final String message) {
        super(message);
    }

    public FilmNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
