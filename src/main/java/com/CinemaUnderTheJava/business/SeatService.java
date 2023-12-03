package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.AlreadyExistsException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.SeatEntity;
import com.cinemaUnderTheJava.database.enums.SeatStatus;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.SeatJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class SeatService {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    public SeatEntity generateSeat(int rowNumber, int seatInRow, SeatStatus seatStatus) {
        SeatEntity seat = new SeatEntity();
        seat.setRowNumber(rowNumber);
        seat.setSeatInRow(seatInRow);
        seat.setStatus(seatStatus);
        return seat;
    }

    @Transactional
    public void verifyAndReserveSeat(Long projectionId, int rowNumber, int seatInRow) {
        ProjectionEntity projection = projectionJpaRepository.findById(projectionId)
                .orElseThrow(() -> new NotFoundException(PROJECTION_NOT_FOUND.getMessage(projectionId)));
        SeatEntity seat = seatJpaRepository.findByProjectionAndRowNumberAndSeatInRow(projection, rowNumber, seatInRow);


        if (seat != null) {
            SeatStatus status = seat.getStatus();

            switch (status) {
                case AVAILABLE -> {
                    seat.setStatus(SeatStatus.TAKEN);
                    seatJpaRepository.save(seat);
                }
                case TAKEN -> throw new AlreadyExistsException(SEAT_IS_TAKEN.getMessage());
                default -> throw new NotFoundException(SEAT_NOT_FOUND.getMessage(rowNumber, seatInRow));
            }
        } else {
            throw new NotFoundException(SEAT_NOT_FOUND.getMessage(rowNumber, seatInRow));
        }
    }
}
