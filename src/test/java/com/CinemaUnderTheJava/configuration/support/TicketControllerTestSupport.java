package com.cinemaUnderTheJava.configuration.support;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface TicketControllerTestSupport {

    RequestSpecification requestSpecification();

    default void reserveTicketSupport(final long userId, final long projectionId, final TicketReservationDto ticketReservationDto) {
        requestSpecification()
                .given()
                .contentType(ContentType.JSON)
                .body(ticketReservationDto)
                .expect()
                .statusCode(HttpStatus.CREATED.value())
                .when()
                .post("/tickets/" + userId + "/" + projectionId);
    }
}
