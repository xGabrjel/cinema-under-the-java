package com.cinemaUnderTheJava.api.dto.projection;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
@Builder
public record ProjectionRequestDto(
        LocalDate date,
        LocalTime time
) {
}
