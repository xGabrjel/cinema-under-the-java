package com.cinemaUnderTheJava.business.exceptions;

public class InvalidFilmCategoryException extends RuntimeException {

    public InvalidFilmCategoryException(final String message) {
        super(message);
    }

    public InvalidFilmCategoryException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
