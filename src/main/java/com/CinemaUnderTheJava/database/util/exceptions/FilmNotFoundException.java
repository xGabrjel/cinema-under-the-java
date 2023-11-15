package com.cinemaUnderTheJava.database.util.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(Long filmId) {
        super("Oops! It looks like the film you were looking for was not found! Film id: [%s]".formatted(filmId));
    }
}
