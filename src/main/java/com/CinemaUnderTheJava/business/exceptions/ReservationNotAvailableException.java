package com.cinemaUnderTheJava.business.exceptions;

public class ReservationNotAvailableException extends RuntimeException {

    public ReservationNotAvailableException(final String message) {
        super(message);
    }

    public ReservationNotAvailableException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
