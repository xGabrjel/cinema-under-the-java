package com.cinemaUnderTheJava.api.dto.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProjectionRequestDto(
        LocalDate date,
        LocalTime time
) {
}
