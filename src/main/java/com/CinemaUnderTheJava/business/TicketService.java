package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.ReservationNotAvailableException;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.business.util.EmailTicketSenderUtil;
import com.cinemaUnderTheJava.business.util.PriceCalculatorUtil;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.TicketStatus;
import com.cinemaUnderTheJava.database.mapper.TicketMapper;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.TicketJpaRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private final TicketJpaRepository ticketJpaRepository;
    private final ProjectionJpaRepository projectionJpaRepository;
    private final PriceCalculatorUtil priceCalculator;
    private final TicketMapper ticketMapper;
    private final UserService userService;
    private final EmailTicketSenderUtil emailTicketSender;
    private final SeatService seatService;

    @Transactional
    public TicketReservedDto reserveTicket(Long projectionId, TicketReservationDto ticketReservationDto, Long userId) throws MessagingException {
        UserEntity user = userService.findUserById(userId);
        ProjectionEntity projection = getProjectionById(projectionId);
        validateReservationTiming(projection);
        TicketEntity ticket = generateTicket(projection, ticketReservationDto, user);
        seatService.verifyAndReserveSeat(projectionId, ticket.getRowNumber(), ticket.getSeatInRow());

        ticketJpaRepository.save(ticket);
        emailTicketSender.sendEmailWithTicket(user.getEmail(), ticket);
        log.info("Reservation Complete! The ticket has been saved. Details: [%s]".formatted(ticket));
        return ticketMapper.entityToDto(ticket);
    }

    private TicketEntity generateTicket(ProjectionEntity projection, TicketReservationDto ticketReservationDto, UserEntity user) {
        log.info("Generating new ticket for projection: [%s]".formatted(projection));
        return TicketEntity.builder()
                .userId(user.getId())
                .name(user.getFirstName().concat(" ").concat(user.getLastName()))
                .filmTitle(projection.getFilm().getTitle())
                .projectionDate(projection.getDate())
                .projectionTime(projection.getTime())
                .ticketPrice(priceCalculator.calculatePriceForProjection(projection, ticketReservationDto))
                .rowNumber(ticketReservationDto.rowNumber())
                .roomNumber(1)
                .seatInRow(ticketReservationDto.seatInRow())
                .status(TicketStatus.ACTIVE)
                .ticketType(ticketReservationDto.ticketType())
                .ticketCurrency(ticketReservationDto.ticketCurrency())
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
        LocalDate currentDay = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        LocalDate projectionDate = projection.getDate();
        LocalTime projectionTime = projection.getTime();

        log.info("Current date: %s".formatted(currentDay));
        log.info("Current time: %s".formatted(currentTime));
        log.info("Projection date: %s".formatted(projectionDate));
        log.info("Projection time: %s".formatted(projectionTime));

        boolean isProjectionInPast = projectionDate.isBefore(currentDay) ||
                (projectionDate.isEqual(currentDay) && projectionTime.isBefore(currentTime.plusMinutes(15)));

        if (isProjectionInPast) {
            throw new ReservationNotAvailableException(RESERVATION_NOT_AVAILABLE.getMessage());
        }
    }

    private ProjectionEntity getProjectionById(Long projectionId) {
        return projectionJpaRepository
                .findById(projectionId)
                .orElseThrow(() -> new NotFoundException(PROJECTION_NOT_FOUND.getMessage(projectionId)));
    }

    private TicketEntity getTicketById(Long ticketId) {
        return ticketJpaRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException(TICKET_NOT_FOUND.getMessage(ticketId)));
    }
}
