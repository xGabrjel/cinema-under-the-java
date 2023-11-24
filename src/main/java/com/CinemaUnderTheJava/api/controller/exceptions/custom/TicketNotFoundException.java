package com.cinemaUnderTheJava.api.controller.exceptions.custom;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(final String message) {
        super(message);
    }

    public TicketNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
