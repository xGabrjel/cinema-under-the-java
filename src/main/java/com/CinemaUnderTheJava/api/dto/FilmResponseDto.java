package com.cinemaUnderTheJava.api.dto;

import com.cinemaUnderTheJava.database.enums.FilmCategory;

public record FilmResponseDto(
        Long id,
        String title,
        FilmCategory category,
        int filmDurationInMinutes
) {
}
