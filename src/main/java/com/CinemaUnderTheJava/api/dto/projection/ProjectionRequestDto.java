package com.cinemaUnderTheJava.api.dto.projection;

import lombok.Builder;
import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

@With
@Builder
public record ProjectionRequestDto(
        LocalDate date,
        LocalTime time
) {
}
