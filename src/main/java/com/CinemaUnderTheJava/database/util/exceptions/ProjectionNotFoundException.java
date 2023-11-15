package com.cinemaUnderTheJava.database.util.exceptions;

import java.time.LocalDate;

public class ProjectionNotFoundException extends RuntimeException {

    public ProjectionNotFoundException(LocalDate date) {
        super("Oops! It looks like the projection you were looking for was not found! Projection date: [%s]".formatted(date));
    }

    public ProjectionNotFoundException(Long id) {
        super("Oops! It looks like the projection you were looking for was not found! Projection id: [%s]".formatted(id));
    }

    public ProjectionNotFoundException() {
        super("Oops! No projections available!");
    }
}
