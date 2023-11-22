package com.cinemaUnderTheJava.business.exceptions;

public class DuplicateFilmException extends RuntimeException {

    public DuplicateFilmException(String message) {
        super(message);
    }

    public DuplicateFilmException(String message, Object... args) {
        super(String.format(message, args));
    }
}
