package com.cinemaUnderTheJava.api.dto;

public record NewUserDto(
        String firstName,
        String lastName,
        String email
) {
}
