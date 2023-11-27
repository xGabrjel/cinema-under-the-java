package com.cinemaUnderTheJava.api.dto.user;

import com.cinemaUnderTheJava.database.enums.UserRole;

public record UserResponseDto(
        String firstName,
        String lastName,
        String email,
        UserRole userRole
) {
}
