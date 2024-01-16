package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.AlreadyExistsException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.business.util.UserVerificationUtil;
import com.cinemaUnderTheJava.configuration.AbstractIT;
import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.mapper.UserMapper;
import com.cinemaUnderTheJava.database.repository.jpa.UserJpaRepository;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import com.cinemaUnderTheJava.security.PasswordEncoderService;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserServiceTest extends AbstractIT {

    private final UserService userService;
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final UserVerificationUtil userVerification;
    private final PasswordEncoderService passwordEncoderService;

    @Test
    void registerUserShouldRegisterNewUserCorrectly() {
        //given
        UserRequestDto userRequestDto = DtoFixtures.someUserRequestDto()
                .withEmail("userServiceTest@gmail.com");

        //when
        NewUserDto newUserDto = userService.registerUser(userRequestDto);

        //then
        assertNotNull(newUserDto);
        assertEquals(userRequestDto.email(), newUserDto.email());
        assertEquals(userRequestDto.firstName(), newUserDto.firstName());
        assertEquals(userRequestDto.lastName(), newUserDto.lastName());
    }

    @Test
    void validatePasswordMatchingExceptionIsThrownCorrectly() {
        //given
        UserRequestDto userRequestDto = DtoFixtures.someUserRequestDto()
                .withEmail("newEmail@gmail.com")
                .withRepeatedPassword("IncorrectPassword");

        //when, then
        assertThrows(ValidationException.class, () -> userService.registerUser(userRequestDto));
    }

    @Test
    void checkIfEmailExistsExceptionIsThrownCorrectly() {
        //given
        UserRequestDto userRequestDto = DtoFixtures.someUserRequestDto()
                .withEmail("newEmail2@gmail.com");

        NewUserDto alreadyRegisteredUser = userService.registerUser(userRequestDto);
        UserRequestDto userRequestDto2 = DtoFixtures.someUserRequestDto()
                .withEmail(alreadyRegisteredUser.email());

        //when, then
        assertThrows(AlreadyExistsException.class, () -> userService.registerUser(userRequestDto2));
    }

    @Test
    void findUserDtoByIdWorksCorrectly() {
        //given
        UserRequestDto userRequestDto = DtoFixtures.someUserRequestDto()
                .withEmail("newEmail3@gmail.com");

        NewUserDto alreadyRegisteredUser = userService.registerUser(userRequestDto);

        //when
        UserResponseDto result = userService.findUserDtoById(alreadyRegisteredUser.id());

        //then
        assertNotNull(result);
    }

    @Test
    void findUserByIdWorksCorrectly() {
        //given
        UserRequestDto userRequestDto = DtoFixtures.someUserRequestDto()
                .withEmail("newEmail4@gmail.com");

        NewUserDto alreadyRegisteredUser = userService.registerUser(userRequestDto);

        //when
        UserEntity result = userService.findUserById(alreadyRegisteredUser.id());

        //then
        assertNotNull(result);
    }

    @Test
    void findUserDtoByIdThrowsExceptionCorrectly() {
        //given, when, then
        assertThrows(NotFoundException.class, () -> userService.findUserDtoById(997L));
    }

    @Test
    void findUserByIdThrowsExceptionCorrectly() {
        //given, when, then
        assertThrows(NotFoundException.class, () -> userService.findUserById(997L));
    }
}
