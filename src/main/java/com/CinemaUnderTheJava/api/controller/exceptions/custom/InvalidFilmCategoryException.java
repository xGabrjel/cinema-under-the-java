package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class InvalidFilmCategoryException extends RuntimeException {

    public InvalidFilmCategoryException(final String message) {
        super(message);
    }

    public InvalidFilmCategoryException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
