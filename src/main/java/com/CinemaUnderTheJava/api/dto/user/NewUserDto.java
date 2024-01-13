package com.cinemaUnderTheJava.api.dto.user;

public record NewUserDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
