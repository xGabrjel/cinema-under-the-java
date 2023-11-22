package com.cinemaUnderTheJava.database.util.exceptions;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String message) {
        super(message);
    }
}
