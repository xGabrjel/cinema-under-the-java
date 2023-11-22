package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.business.TicketService;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    //TODO - need user and dto 2/2
    @PostMapping("/{projectionId}")
    public ResponseEntity<TicketEntity> reserveTicket(@PathVariable Long projectionId) {
        return new ResponseEntity<>(ticketService.ticketReservation(projectionId), HttpStatus.CREATED);
    }
}
