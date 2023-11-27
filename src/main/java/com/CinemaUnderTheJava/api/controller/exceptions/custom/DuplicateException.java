package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class DuplicateException extends RuntimeException {

    public DuplicateException(final String message) {
        super(message);
    }

    public DuplicateException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
