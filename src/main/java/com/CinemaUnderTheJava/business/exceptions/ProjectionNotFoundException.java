package com.cinemaUnderTheJava.business.exceptions;

public class ProjectionNotFoundException extends RuntimeException {

    public ProjectionNotFoundException(String message) {
        super(message);
    }

    public ProjectionNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
