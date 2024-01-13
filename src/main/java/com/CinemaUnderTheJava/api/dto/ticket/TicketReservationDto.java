package com.cinemaUnderTheJava.api.dto.ticket;

import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketType;
import lombok.Builder;

@Builder
public record TicketReservationDto(
        TicketType ticketType,
        TicketCurrency ticketCurrency,
        int rowNumber,
        int seatInRow
) {
}
