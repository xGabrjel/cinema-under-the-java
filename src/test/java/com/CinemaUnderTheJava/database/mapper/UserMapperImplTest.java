package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperImplTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void shouldMapEntityToDto() {
        //given
        UserEntity userEntity = EntityFixtures.someUserEntity()
                .withFirstName("Test")
                .withLastName("Testowski")
                .withEmail("test.testowski@gmail.com")
                .withActivationStatus(ActivationStatus.ACTIVE);

        //when
        UserResponseDto userResponseDto = userMapper.entityToDto(userEntity);

        //then
        assertEquals("Test", userResponseDto.firstName());
        assertEquals("Testowski", userResponseDto.lastName());
        assertEquals("test.testowski@gmail.com", userResponseDto.email());
        assertEquals(ActivationStatus.ACTIVE, userResponseDto.activationStatus());
    }

    @Test
    void shouldReturnNullForNullEntity() {
        //given
        UserEntity userEntity = null;

        //when
        UserResponseDto userResponseDto = userMapper.entityToDto(userEntity);

        //then
        assertNull(userResponseDto);
    }

    @Test
    void shouldMapNewUserEntityToDto() {
        //given
        UserEntity userEntity = EntityFixtures.someUserEntity()
                .withFirstName("Jan")
                .withLastName("Jankowski")
                .withEmail("jan.jankowski@gmail.com");
        //when
        NewUserDto newUserDto = userMapper.newUserEntityToDto(userEntity);

        //then
        assertEquals("Jan", newUserDto.firstName());
        assertEquals("Jankowski", newUserDto.lastName());
        assertEquals("jan.jankowski@gmail.com", newUserDto.email());
    }

    @Test
    void shouldReturnNullForNullNewUserEntity() {
        //given
        UserEntity userEntity = null;

        //when
        NewUserDto newUserDto = userMapper.newUserEntityToDto(userEntity);

        //then
        assertNull(newUserDto);
    }
}