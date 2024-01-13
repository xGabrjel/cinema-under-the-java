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
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketJpaRepository ticketJpaRepository;
    @Mock
    private ProjectionJpaRepository projectionJpaRepository;
    @Mock
    private PriceCalculatorUtil priceCalculator;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private UserService userService;
    @Mock
    private EmailTicketSenderUtil emailTicketSender;
    @Mock
    private SeatService seatService;

    @Test
    void cancelTicketShouldCancelTicket() {
        //given
        Long ticketId = 1L;
        TicketEntity ticketEntity = EntityFixtures.someTicket();

        when(ticketJpaRepository.findById(ticketId)).thenReturn(Optional.of(ticketEntity));

        //when
        assertDoesNotThrow(() -> ticketService.cancelTicket(ticketId));

        //then
        assertEquals(TicketStatus.CANCELLED, ticketEntity.getStatus());
        verify(ticketJpaRepository, times(1)).delete(eq(ticketEntity));
    }

    @Test
    void validateReservationTimingShouldThrowExceptionForPastProjection() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        projectionEntity.setDate(LocalDate.now().minusDays(1));
        projectionEntity.setTime(LocalTime.now().minusMinutes(30));

        //when, then
        assertThrows(ReservationNotAvailableException.class, () -> ticketService.validateReservationTiming(projectionEntity));
    }

    @Test
    void validateReservationTimingShouldNotThrowExceptionForFutureProjection() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        projectionEntity.setDate(LocalDate.now().plusDays(1));
        projectionEntity.setTime(LocalTime.now().plusMinutes(30));

        //when, then
        assertDoesNotThrow(() -> ticketService.validateReservationTiming(projectionEntity));
    }

    @Test
    void generateTicketShouldCreateTicketEntity() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        TicketReservationDto ticketReservationDto = DtoFixtures.someTicketReservationDto();
        UserEntity userEntity = EntityFixtures.someUserEntity();

        //when
        TicketEntity ticketEntity = ticketService.generateTicket(projectionEntity, ticketReservationDto, userEntity);

        //then
        assertNotNull(ticketEntity);
        assertEquals(userEntity.getId(), ticketEntity.getUserId());
        assertEquals(userEntity.getFirstName() + " " + userEntity.getLastName(), ticketEntity.getName());
        assertEquals(projectionEntity.getFilm().getTitle(), ticketEntity.getFilmTitle());
        assertEquals(projectionEntity.getDate(), ticketEntity.getProjectionDate());
        assertEquals(projectionEntity.getTime(), ticketEntity.getProjectionTime());
    }

    @Test
    void getProjectionByIdShouldReturnProjectionEntityIfExists() {
        //given
        Long projectionId = 1L;
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();

        when(projectionJpaRepository.findById(projectionId)).thenReturn(Optional.of(projectionEntity));

        //when
        ProjectionEntity result = ticketService.getProjectionById(projectionId);

        //then
        assertNotNull(result);
        assertEquals(projectionEntity, result);
    }

    @Test
    void getProjectionByIdShouldThrowNotFoundExceptionIfNotExists() {
        //given
        Long projectionId = 10L;

        when(projectionJpaRepository.findById(projectionId)).thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> ticketService.getProjectionById(projectionId));
    }

    @Test
    void getTicketByIdShouldReturnTicketEntityIfExists() {
        //given
        Long ticketId = 1L;
        TicketEntity ticketEntity = EntityFixtures.someTicket();

        when(ticketJpaRepository.findById(ticketId)).thenReturn(Optional.of(ticketEntity));

        //when
        TicketEntity result = ticketService.getTicketById(ticketId);

        //then
        assertNotNull(result);
        assertEquals(ticketEntity, result);
    }

    @Test
    void getTicketByIdShouldThrowNotFoundExceptionIfNotExists() {
        //given
        Long ticketId = 10L;

        when(ticketJpaRepository.findById(ticketId)).thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> ticketService.getTicketById(ticketId));
    }

    @Test
    void reserveTicketShouldReserveTicketAndSendEmail() throws MessagingException {
        //given
        TicketReservationDto ticketReservationDto = DtoFixtures.someTicketReservationDto();
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        UserEntity userEntity = EntityFixtures.someUserEntity();
        TicketReservedDto ticketReservedDto = DtoFixtures.someTicketReservedDto();

        when(userService.findUserById(userEntity.getId())).thenReturn(userEntity);
        when(projectionJpaRepository.findById(projectionEntity.getId())).thenReturn(Optional.of(projectionEntity));
        when(priceCalculator.calculatePriceForProjection(any(), any())).thenReturn(BigDecimal.TEN);
        when(ticketMapper.entityToDto(any())).thenReturn(ticketReservedDto);

        //when
        TicketReservedDto result = ticketService.reserveTicket(projectionEntity.getId(), ticketReservationDto, userEntity.getId());

        //then
        verify(ticketJpaRepository, times(1)).save(any());
        verify(emailTicketSender, times(1)).sendEmailWithTicket(eq(userEntity.getEmail()), any());
        verify(seatService, times(1)).verifyAndReserveSeat(eq(projectionEntity.getId()), anyInt(), anyInt());

        assertThat(result).isNotNull();
        assertThat(result.filmTitle()).isEqualTo(projectionEntity.getFilm().getTitle());
        assertThat(result.projectionDate()).isEqualTo(projectionEntity.getDate());
        assertThat(result.projectionTime()).isEqualTo(projectionEntity.getTime());
        assertThat(result.ticketStatus()).isEqualTo(TicketStatus.ACTIVE);
        assertThat(result.seatInRow()).isEqualTo(ticketReservationDto.seatInRow());
        assertThat(result.rowNumber()).isEqualTo(ticketReservationDto.rowNumber());
    }
}