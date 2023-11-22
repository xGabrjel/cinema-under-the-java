package com.cinemaUnderTheJava.database.util.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }
}
