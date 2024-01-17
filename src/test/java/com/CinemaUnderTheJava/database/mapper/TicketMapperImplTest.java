package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TicketMapperImplTest {

    private final TicketMapper ticketMapper = new TicketMapperImpl();

    @Test
    void shouldMapEntityToDto() {
        //given
        TicketEntity ticketEntity = EntityFixtures.someTicketEntity()
                .withFilmTitle("Inception")
                .withProjectionDate(LocalDate.of(2022, 1, 1))
                .withProjectionTime(LocalTime.of(12, 0))
                .withRoomNumber(1)
                .withSeatInRow(2);

        //when
        TicketReservedDto ticketReservedDto = ticketMapper.entityToDto(ticketEntity);

        //then
        assertEquals("Inception", ticketReservedDto.filmTitle());
        assertEquals(LocalDate.of(2022, 1, 1), ticketReservedDto.projectionDate());
        assertEquals(LocalTime.of(12, 0), ticketReservedDto.projectionTime());
        assertEquals(1, ticketReservedDto.rowNumber());
        assertEquals(2, ticketReservedDto.seatInRow());
    }

    @Test
    void shouldReturnNullForNullEntity() {
        //given
        TicketEntity ticketEntity = null;

        //when
        TicketReservedDto ticketReservedDto = ticketMapper.entityToDto(ticketEntity);

        //then
        assertNull(ticketReservedDto);
    }
}