package com.cinemaUnderTheJava.api.dto;

import com.cinemaUnderTheJava.database.util.FilmCategory;

public record FilmRequestDto(String title, FilmCategory category, int filmDurationInMinutes) {
}
