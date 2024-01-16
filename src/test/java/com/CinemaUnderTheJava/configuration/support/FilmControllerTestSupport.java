package com.cinemaUnderTheJava.configuration.support;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.notNullValue;

public interface FilmControllerTestSupport {

    RequestSpecification requestSpecification();

    default void allFilmsSupport() {
        requestSpecification()
                .given()
                .when()
                .get("/films")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }

    default void filmsByCategorySupport(final FilmCategory horror) {
        requestSpecification()
                .given()
                .param("category", horror)
                .when()
                .get("/films/category")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }

    default void newHorrorSupport(final FilmRequestDto newFilm) {
        requestSpecification()
                .given()
                .contentType(ContentType.JSON)
                .body(newFilm)
                .expect()
                .statusCode(HttpStatus.CREATED.value())
                .when()
                .post("/films/newFilm");
    }

    default void deleteFilmSupport(final long filmId) {
        requestSpecification()
                .given()
                .when()
                .delete("/films/delete/" + filmId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    default void filmByIdSupport(final long filmId) {
        requestSpecification()
                .given()
                .when()
                .get("/films/" + filmId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }
}
