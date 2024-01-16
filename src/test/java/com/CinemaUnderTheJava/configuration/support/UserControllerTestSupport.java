package com.cinemaUnderTheJava.configuration.support;

import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public interface UserControllerTestSupport {

    RequestSpecification requestSpecification();

    default void getUserById(final long id) {
        requestSpecification()
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body(notNullValue());
    }

    default void activateAccountByToken(final String token) {
        requestSpecification()
                .when()
                .get("users/activation?token=" + token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("Activation has been completed!"));
    }


    default void registerUser(final UserRequestDto userRequestDto) {
        requestSpecification()
                .given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .expect()
                .statusCode(HttpStatus.CREATED.value())
                .when()
                .post("/users/registration");
    }
}
