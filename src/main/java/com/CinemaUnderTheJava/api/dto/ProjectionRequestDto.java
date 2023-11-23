package com.cinemaUnderTheJava.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProjectionRequestDto(
        LocalDate date,
        LocalTime time
) {
}
