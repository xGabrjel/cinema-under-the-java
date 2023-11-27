package com.cinemaUnderTheJava.api.dto.user;

import com.cinemaUnderTheJava.database.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 8, max = 16)
        String password,
        @NotBlank
        @Size(min = 8, max = 16)
        String repeatedPassword,
        UserRole role
) {
}
