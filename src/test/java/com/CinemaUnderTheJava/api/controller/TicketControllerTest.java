package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.configuration.RestAssureConfigurationTestBase;
import com.cinemaUnderTheJava.configuration.support.TicketControllerTestSupport;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import org.junit.jupiter.api.Test;

class TicketControllerTest extends RestAssureConfigurationTestBase implements TicketControllerTestSupport {

    @Test
    void reserveTicketWorksCorrectly() {
        //given
        long userId = 999;
        long projectionId = 5;
        TicketReservationDto ticketReservationDto = DtoFixtures.someTicketReservationDto();

        //when, then
        reserveTicketSupport(userId, projectionId, ticketReservationDto);
    }
}