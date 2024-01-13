package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.AlreadyExistsException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.SeatEntity;
import com.cinemaUnderTheJava.database.enums.SeatStatus;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.SeatJpaRepository;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @InjectMocks
    private SeatService seatService;

    @Mock
    private ProjectionJpaRepository projectionJpaRepository;

    @Mock
    private SeatJpaRepository seatJpaRepository;

    @Test
    void verifyAndReserveSeatShouldReserveAvailableSeat() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        SeatEntity seatEntity = EntityFixtures.someSeatEntity();

        when(projectionJpaRepository.findById(projectionEntity.getId())).thenReturn(Optional.of(projectionEntity));
        when(seatJpaRepository.findByProjectionAndRowNumberAndSeatInRow(projectionEntity, seatEntity.getRowNumber(), seatEntity.getSeatInRow()))
                .thenReturn(seatEntity);

        // when
        assertDoesNotThrow(() -> seatService.verifyAndReserveSeat(projectionEntity.getId(), seatEntity.getRowNumber(), seatEntity.getSeatInRow()));

        // then
        assertEquals(SeatStatus.TAKEN, seatEntity.getStatus());
        verify(seatJpaRepository, times(1)).save(seatEntity);
    }

    @Test
    void verifyAndReserveSeatShouldThrowAlreadyExistsExceptionForTakenSeat() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        SeatEntity takenSeat = EntityFixtures.someSeatEntity()
                .withStatus(SeatStatus.TAKEN);

        when(projectionJpaRepository.findById(projectionEntity.getId())).thenReturn(Optional.of(projectionEntity));
        when(seatJpaRepository.findByProjectionAndRowNumberAndSeatInRow(projectionEntity, takenSeat.getRowNumber(), takenSeat.getSeatInRow())).thenReturn(takenSeat);

        // when, then
        assertThrows(AlreadyExistsException.class, () -> seatService.verifyAndReserveSeat(projectionEntity.getId(), takenSeat.getRowNumber(), takenSeat.getSeatInRow()));
        verify(seatJpaRepository, never()).save(any());
    }

    @Test
    void verifyAndReserveSeatShouldThrowNotFoundExceptionForUnknownSeat() {
        // given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity();
        SeatEntity seatEntity = EntityFixtures.someSeatEntity();

        when(projectionJpaRepository.findById(projectionEntity.getId())).thenReturn(Optional.of(projectionEntity));
        when(seatJpaRepository.findByProjectionAndRowNumberAndSeatInRow(projectionEntity, seatEntity.getRowNumber(), seatEntity.getSeatInRow())).thenReturn(null);

        // when, then
        assertThrows(NotFoundException.class, () -> seatService.verifyAndReserveSeat(projectionEntity.getId(), seatEntity.getRowNumber(), seatEntity.getSeatInRow()));
        verify(seatJpaRepository, never()).save(any());
    }
}