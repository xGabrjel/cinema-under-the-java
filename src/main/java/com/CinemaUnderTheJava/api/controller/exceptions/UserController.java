package com.cinemaUnderTheJava.api.controller.exceptions;

import com.cinemaUnderTheJava.api.dto.NewUserDto;
import com.cinemaUnderTheJava.api.dto.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.UserResponseDto;
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
    public ResponseEntity<NewUserDto> registration(@Valid @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findByUserId(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }
}
