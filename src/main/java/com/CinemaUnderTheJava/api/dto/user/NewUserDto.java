package com.cinemaUnderTheJava.api.dto.user;

import java.util.UUID;

public record NewUserDto(
        UUID uuid,
        String firstName,
        String lastName,
        String email
) {
}
