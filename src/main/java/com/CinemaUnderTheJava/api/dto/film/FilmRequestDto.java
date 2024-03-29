package com.cinemaUnderTheJava.api.dto.film;

import com.cinemaUnderTheJava.database.enums.FilmCategory;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record FilmRequestDto(
        String title,
        FilmCategory category,
        int filmDurationInMinutes
) {
}
