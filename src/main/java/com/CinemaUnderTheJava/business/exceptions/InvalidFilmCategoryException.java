package com.cinemaUnderTheJava.business.exceptions;

public class InvalidFilmCategoryException extends RuntimeException {

    public InvalidFilmCategoryException(String message) {
        super(message);
    }

    public InvalidFilmCategoryException(String message, Object... args) {
        super(String.format(message, args));
    }
}
