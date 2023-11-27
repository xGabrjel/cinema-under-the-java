package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(final String message) {
        super(message);
    }

    public AlreadyExistsException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
