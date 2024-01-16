package com.cinemaUnderTheJava.api.controller.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExceptionMessages {

    FILM_NOT_FOUND("The film you were looking for was not found! Ticket ID: "),
    DUPLICATE_FILM("The film with this title already exists! Title: "),

    RESERVATION_NOT_AVAILABLE("Reservation is no longer available!"),
    PROJECTION_NOT_FOUND("The projection you were looking for was not found! Projection ID: "),
    TICKET_NOT_FOUND("Unable to locate the requested ticket in the system! Ticket ID: "),
    PROJECTIONS_NOT_FOUND_WITH_DATE("Unable to locate any projections for the given date. Date: "),

    INSUFFICIENT_TIME_GAP("Time gap is insufficient to schedule a new projection."),
    TOO_LATE_TO_SCHEDULE("It is not possible to schedule a new projection before the current time."),
    TOO_MANY_PROJECTIONS("It is not possible to schedule another projection. Number of current projections: "),

    PASSWORDS_DO_NOT_MATCH("The provided passwords do not match."),
    EMAIL_IS_TAKEN("The email address is already in use. Email: "),
    USER_NOT_FOUND("The user you were looking for was not found. User ID: "),

    SEAT_NOT_FOUND("The seat you were looking for was not found. Row and Seat: "),
    SEAT_IS_TAKEN("The seat you were looking for was already taken.");

    private final String message;

    public String getMessage(Object... args) {
        return String.format(this.message, args);
    }
}
