package com.cinemaUnderTheJava.api.dto.ticket;

import com.cinemaUnderTheJava.database.enums.TicketStatus;
import com.cinemaUnderTheJava.database.enums.TicketType;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketResponseDto(
        String name,
        String filmTitle,
        LocalDate projectionDate,
        LocalTime projectionTime,
        TicketStatus ticketStatus,
        TicketType ticketType,
        int ticketPrice
) {
}
