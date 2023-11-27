package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.business.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{projectionId}")
    public ResponseEntity<TicketReservedDto> reserveTicket(
            @PathVariable Long projectionId,
            @RequestBody TicketReservationDto ticketReservationDto,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(ticketService.reserveTicket(projectionId, ticketReservationDto, userId), HttpStatus.CREATED);
    }
}
