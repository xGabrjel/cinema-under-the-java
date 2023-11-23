package com.cinemaUnderTheJava.business.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(final String message) {
        super(message);
    }

    public TicketNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
