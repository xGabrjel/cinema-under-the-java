package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.business.exceptions.ProjectionNotFoundException;
import com.cinemaUnderTheJava.business.exceptions.ReservationNotAvailableException;
import com.cinemaUnderTheJava.business.exceptions.TicketNotFoundException;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.cinemaUnderTheJava.database.enums.TicketStatus;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.TicketJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.cinemaUnderTheJava.business.ExceptionMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private final TicketJpaRepository ticketJpaRepository;
    private final ProjectionJpaRepository projectionJpaRepository;

    @Transactional
    public TicketEntity ticketReservation(Long projectionId) {
        ProjectionEntity projection = getProjectionById(projectionId);
        validateReservationTiming(projection);
        TicketEntity ticket = generateTicket(projection);

        ticketJpaRepository.save(ticket);
        log.info("Reservation Complete! The ticket has been saved. Details: [%s]".formatted(ticket));
        return ticket;
    }

    private TicketEntity generateTicket(ProjectionEntity projection) {
        log.info("Generating new ticket for projection: [%S]".formatted(projection));

        //TODO - need user and dto 1/2
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
        TicketEntity ticket = getTicketById(ticketId);
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketJpaRepository.delete(ticket);
        log.info("Cancel Complete! The ticket has been cancelled. Details: [%s]".formatted(ticket));
    }

    private void validateReservationTiming(ProjectionEntity projection) {
        if (projection.getDate().isAfter(LocalDate.now()) || Duration.between(projection.getTime(), LocalTime.now()).toMinutes() >= 15) {
            throw new ReservationNotAvailableException(RESERVATION_NOT_AVAILABLE);
        }
    }

    private ProjectionEntity getProjectionById(Long projectionId) {
        return projectionJpaRepository
                .findById(projectionId)
                .orElseThrow(() -> new ProjectionNotFoundException(PROJECTION_NOT_FOUND, projectionId));
    }

    private TicketEntity getTicketById(Long ticketId) {
        return ticketJpaRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(TICKET_NOT_FOUND, ticketId));
    }
}
