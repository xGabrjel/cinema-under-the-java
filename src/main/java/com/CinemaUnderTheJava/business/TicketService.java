package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.TicketJpaRepository;
import com.cinemaUnderTheJava.database.util.TicketStatus;
import com.cinemaUnderTheJava.database.util.exceptions.ProjectionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private final TicketJpaRepository ticketJpaRepository;
    private final ProjectionJpaRepository projectionJpaRepository;

    @Transactional
    public TicketEntity ticketReservation(Long projectionId) {

        ProjectionEntity projection = projectionJpaRepository
                .findById(projectionId)
                .orElseThrow(() -> new ProjectionNotFoundException(projectionId));

        validateBookingTiming(projection);
        TicketEntity ticket = generateTicket(projection);

        ticketJpaRepository.save(ticket);
        log.info("Reservation Complete! The ticket has been saved. Details: [%s]".formatted(ticket));
        return ticket;
    }

    private TicketEntity generateTicket(ProjectionEntity projection) {
        log.info("Generating new ticket for projection: [%S]".formatted(projection));

        //TODO - need user and dto
        return TicketEntity.builder()
//                .name()
                .filmTitle(projection.getFilm().getTitle())
                .projectionDate(projection.getDate())
                .projectionTime(projection.getTime())
//                .ticketPrice()
//                .rowsNumber()
                .roomNumber(1)
//                .seatsInRow()
//                .userId()
                .status(TicketStatus.ACTIVE)
//                .ticketType()
//                .ticketCurrency()
                .build();
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        TicketEntity ticket = ticketJpaRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found. Unable to locate the requested ticket in the system."));

        ticket.setStatus(TicketStatus.CANCELLED);
        ticketJpaRepository.delete(ticket);
        log.info("Cancel Complete! The ticket has been cancelled. Details: [%s]".formatted(ticket));
    }

    private void validateBookingTiming(ProjectionEntity projection) {
        if (projection.getDate().isAfter(LocalDate.now()) || Duration.between(projection.getTime(), LocalTime.now()).toMinutes() >= 15) {
            throw new RuntimeException("Booking a ticket is no longer available.");
        }
    }
}
