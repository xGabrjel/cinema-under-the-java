package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
