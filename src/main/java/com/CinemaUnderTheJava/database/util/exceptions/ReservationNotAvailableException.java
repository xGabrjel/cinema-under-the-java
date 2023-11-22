package com.cinemaUnderTheJava.database.util.exceptions;

public class ReservationNotAvailableException extends RuntimeException {
    public ReservationNotAvailableException(String message) {
        super(message);
    }
}
