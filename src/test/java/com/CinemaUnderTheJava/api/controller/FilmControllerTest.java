package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.configuration.RestAssureConfigurationTestBase;
import com.cinemaUnderTheJava.configuration.support.FilmControllerTestSupport;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import org.junit.jupiter.api.Test;

class FilmControllerTest extends RestAssureConfigurationTestBase implements FilmControllerTestSupport {

    @Test
    void getAllFilmsWorksCorrectly() {
        //given, when, then
        allFilmsSupport();
    }

    @Test
    void getFilmsByCategoryWorksCorrectly() {
        //given
        FilmCategory horror = FilmCategory.HORROR;

        //when, then
        filmsByCategorySupport(horror);
    }

    @Test
    void savingNewFilmWorksCorrectly() {
        //given
        FilmRequestDto newFilm = DtoFixtures.someFilmRequestDto()
                .withTitle("Testing Controllers")
                .withCategory(FilmCategory.HORROR);

        //when, then
        newHorrorSupport(newFilm);
    }

    @Test
    void deleteFilmWorksCorrectly() {
        //given
        long filmId = 13;

        //when, then
        deleteFilmSupport(filmId);
    }

    @Test
    void getFilmByIdWorksCorrectly() {
        //given
        long filmId = 32;

        //when, then
        filmByIdSupport(filmId);
    }
}