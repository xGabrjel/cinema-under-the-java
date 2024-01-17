package com.cinemaUnderTheJava.api.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

@With
@Builder
public record ProjectionRequestDto(
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
