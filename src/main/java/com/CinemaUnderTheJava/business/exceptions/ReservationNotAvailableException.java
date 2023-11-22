package com.cinemaUnderTheJava.business.exceptions;

public class ReservationNotAvailableException extends RuntimeException {

    public ReservationNotAvailableException(String message) {
        super(message);
    }

    public ReservationNotAvailableException(String message, Object... args) {
        super(String.format(message, args));
    }
}
