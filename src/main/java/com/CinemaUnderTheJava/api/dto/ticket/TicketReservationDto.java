package com.cinemaUnderTheJava.api.dto.ticket;

import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketType;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record TicketReservationDto(
        TicketType ticketType,
        TicketCurrency ticketCurrency,
        int rowNumber,
        int seatInRow
) {
}
