package com.cinemaUnderTheJava.api.dto.user;

import com.cinemaUnderTheJava.database.enums.ActivationStatus;

public record UserResponseDto(
        String firstName,
        String lastName,
        String email,
        ActivationStatus activationStatus
) {
}
