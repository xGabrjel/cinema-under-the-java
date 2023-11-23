package com.cinemaUnderTheJava.business.exceptions;

public class ProjectionNotFoundException extends RuntimeException {

    public ProjectionNotFoundException(final String message) {
        super(message);
    }

    public ProjectionNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
