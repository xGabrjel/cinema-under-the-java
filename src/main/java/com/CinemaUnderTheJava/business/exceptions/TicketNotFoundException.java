package com.cinemaUnderTheJava.business.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
