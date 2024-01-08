package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.business.UserService;
import com.cinemaUnderTheJava.business.util.UserVerificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cinemaUnderTheJava.api.controller.UserController.ControllerRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class UserController {

    private final UserService userService;
    private final UserVerificationUtil userVerification;

    @PostMapping(REGISTRATION)
    public ResponseEntity<NewUserDto> registration(
            @RequestBody UserRequestDto userRequestDto
    ) {
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    public ResponseEntity<UserResponseDto> findByUserId(
            @PathVariable Long id
    ) {
        UserResponseDto userResponseDto = userService.findUserDtoById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(ACTIVATION)
    public ResponseEntity<String> userAccountActivation(
            @RequestParam("token") String token
    ) {
        userVerification.activateUserAccount(token);
        return ResponseEntity.ok("Activation has been completed!");
    }

    static final class ControllerRoutes {
        static final String ROOT = "/users";
        static final String REGISTRATION = "/registration";
        static final String ID = "/{id}";
        static final String ACTIVATION = "/activation";
    }
}

