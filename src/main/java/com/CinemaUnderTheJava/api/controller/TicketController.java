package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.TicketReservedDto;
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

    //TODO - need user 2/2
    @PostMapping("/{projectionId}")
    public ResponseEntity<TicketReservedDto> reserveTicket(
            @PathVariable Long projectionId,
            @RequestBody TicketReservationDto ticketReservationDto
            ) {
        return new ResponseEntity<>(ticketService.reserveTicket(projectionId, ticketReservationDto), HttpStatus.CREATED);
    }
}
