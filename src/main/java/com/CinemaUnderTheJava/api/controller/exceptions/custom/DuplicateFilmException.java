package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class DuplicateFilmException extends RuntimeException {

    public DuplicateFilmException(final String message) {
        super(message);
    }

    public DuplicateFilmException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
