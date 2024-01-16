package com.cinemaUnderTheJava.configuration.support;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public interface ProjectionControllerTestSupport {

    RequestSpecification requestSpecification();

    default void saveProjectionSupport(final long filmId, final ProjectionRequestDto projectionRequestDto) {
        requestSpecification()
                .given()
                .contentType(ContentType.JSON)
                .body(projectionRequestDto)
                .expect()
                .statusCode(HttpStatus.CREATED.value())
                .when()
                .post("/projections/" + filmId);
    }

    default void getProjectionSupport(final LocalDate localDate) {
        requestSpecification()
                .given()
                .param("date", localDate.toString())
                .when()
                .get("/projections")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body(not(empty()));
    }

    default void findAllSupport() {
        requestSpecification()
                .given()
                .when()
                .get("/projections/all")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body(not(empty()));
    }

    default void getAvailableSeatsSupport(final long projectionId) {
        requestSpecification()
                .given()
                .when()
                .get("/projections/" + projectionId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }
}
