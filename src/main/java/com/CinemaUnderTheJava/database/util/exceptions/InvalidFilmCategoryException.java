package com.cinemaUnderTheJava.database.util.exceptions;

public class InvalidFilmCategoryException extends RuntimeException {

    public InvalidFilmCategoryException() {
        super("Oops! Wrong category format!");
    }
}
