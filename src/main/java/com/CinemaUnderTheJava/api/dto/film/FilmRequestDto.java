package com.cinemaUnderTheJava.api.dto.film;

import com.cinemaUnderTheJava.database.enums.FilmCategory;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record FilmRequestDto(
        String title,
        FilmCategory category,
        int filmDurationInMinutes
) {
}
