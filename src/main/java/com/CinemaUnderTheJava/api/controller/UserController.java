package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.business.UserService;
import com.cinemaUnderTheJava.business.util.UserVerificationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cinemaUnderTheJava.api.controller.UserController.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class UserController {

    static final String ROOT = "/users";
    static final String REGISTRATION = "/registration";
    static final String ID = "/{id}";
    static final String ACTIVATION = "/activation";

    static final String REGISTER_A_NEW_USER = "Register a new user";
    static final String RETRIEVE_USER_INFORMATION_MESSAGE = "Retrieve user information by ID";
    static final String ACTIVATE_USER_ACCOUNT_MESSAGE = "Activate user account based on the provided token";

    private final UserService userService;
    private final UserVerificationUtil userVerification;

    @PostMapping(REGISTRATION)
    @Operation(summary = REGISTER_A_NEW_USER)
    public ResponseEntity<NewUserDto> registration(
            @RequestBody UserRequestDto userRequestDto
    ) {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    @Operation(summary = RETRIEVE_USER_INFORMATION_MESSAGE)
    public ResponseEntity<UserResponseDto> findByUserId(
            @PathVariable Long id
    ) {
        UserResponseDto userResponseDto = userService.findUserDtoById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(ACTIVATION)
    @Operation(summary = ACTIVATE_USER_ACCOUNT_MESSAGE)
    public ResponseEntity<String> userAccountActivation(
            @RequestParam("token") String token
    ) {
        userVerification.activateUserAccount(token);
        return ResponseEntity.ok("Activation has been completed!");
    }
}

