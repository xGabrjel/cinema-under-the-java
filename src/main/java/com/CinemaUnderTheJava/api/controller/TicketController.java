package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.business.TicketService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cinemaUnderTheJava.api.controller.TicketController.ControllerRoutes.PROJECTION_ID;
import static com.cinemaUnderTheJava.api.controller.TicketController.ControllerRoutes.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class TicketController {

    private final TicketService ticketService;

    @PostMapping(PROJECTION_ID)
    public ResponseEntity<TicketReservedDto> reserveTicket(
            @PathVariable Long projectionId,
            @RequestBody TicketReservationDto ticketReservationDto,
            @PathVariable Long userId
    ) throws MessagingException {
        return new ResponseEntity<>(ticketService.reserveTicket(projectionId, ticketReservationDto, userId), HttpStatus.CREATED);
    }

    static final class ControllerRoutes {
        static final String ROOT = "/ticket";
        static final String PROJECTION_ID = "/{userId}/{projectionId}";
    }
}
