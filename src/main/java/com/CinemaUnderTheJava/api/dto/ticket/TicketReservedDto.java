package com.cinemaUnderTheJava.api.dto.ticket;

import com.cinemaUnderTheJava.database.enums.TicketStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record TicketReservedDto(
        String filmTitle,
        LocalDate projectionDate,
        LocalTime projectionTime,
        int rowNumber,
        int seatInRow,
        TicketStatus ticketStatus
) {
}
