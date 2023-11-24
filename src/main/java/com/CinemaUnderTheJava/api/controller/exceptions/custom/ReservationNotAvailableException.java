package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class ReservationNotAvailableException extends RuntimeException {

    public ReservationNotAvailableException(final String message) {
        super(message);
    }

    public ReservationNotAvailableException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
