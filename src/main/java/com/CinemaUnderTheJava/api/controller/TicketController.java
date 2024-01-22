package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.business.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cinemaUnderTheJava.api.controller.TicketController.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class TicketController {

    static final String ROOT = "/tickets";
    static final String PROJECTION_ID = "/{userId}/{projectionId}";

    static final String RESERVE_TICKET_MESSAGE = "Reserve a ticket for a specific projection";

    private final TicketService ticketService;

    @PostMapping(PROJECTION_ID)
    @Operation(summary = RESERVE_TICKET_MESSAGE)
    public ResponseEntity<TicketReservedDto> reserveTicket(
            @PathVariable Long projectionId,
            @RequestBody TicketReservationDto ticketReservationDto,
            @PathVariable Long userId
    ) throws MessagingException {
        return new ResponseEntity<>(ticketService.reserveTicket(projectionId, ticketReservationDto, userId), HttpStatus.CREATED);
    }
}
