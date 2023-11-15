package com.cinemaUnderTheJava.database.util.exceptions;

public class DuplicateFilmException extends RuntimeException {
    public DuplicateFilmException(String title) {
        super("Sorry, a film with this title already exists! Title: [%s]".formatted(title));
    }
}
