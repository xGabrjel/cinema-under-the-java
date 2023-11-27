package com.cinemaUnderTheJava.api.dto.user;

public record NewUserDto(
        String firstName,
        String lastName,
        String email
) {
}
