package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.business.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<NewUserDto> registration(
            @Valid @RequestBody UserRequestDto userRequestDto
    ) {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findByUserId(
            @PathVariable Long id
    ) {
        UserResponseDto userResponseDto = userService.findUserDtoById(id);
        return ResponseEntity.ok(userResponseDto);
    }
}
