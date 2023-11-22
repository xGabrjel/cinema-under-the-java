package com.cinemaUnderTheJava.api.dto;

import com.cinemaUnderTheJava.database.enums.FilmCategory;

public record FilmRequestDto(String title, FilmCategory category, int filmDurationInMinutes) {
}
