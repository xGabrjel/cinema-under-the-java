package com.cinemaUnderTheJava.api.dto;

import com.cinemaUnderTheJava.database.entity.FilmEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProjectionResponseDto(Long id, LocalDate date, LocalTime time, FilmEntity film) {
}
