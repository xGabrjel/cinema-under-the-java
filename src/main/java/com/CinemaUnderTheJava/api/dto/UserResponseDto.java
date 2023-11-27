package com.cinemaUnderTheJava.api.dto;

import com.cinemaUnderTheJava.database.enums.UserRole;

public record UserResponseDto(
        String firstName,
        String lastName,
        String email,
        UserRole userRole
) {
}
