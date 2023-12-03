package com.cinemaUnderTheJava.api.dto.seat;

import com.cinemaUnderTheJava.database.entity.SeatEntity;

import java.util.List;

public record AvailableSeatsDto(
        List<SeatEntity> seats
) {
}
