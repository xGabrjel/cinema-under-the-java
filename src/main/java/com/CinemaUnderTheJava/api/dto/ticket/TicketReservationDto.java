package com.cinemaUnderTheJava.api.dto.ticket;

import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketType;

public record TicketReservationDto(
        TicketType ticketType,
        TicketCurrency ticketCurrency,
        int rowNumber,
        int seatInRow
) {
}