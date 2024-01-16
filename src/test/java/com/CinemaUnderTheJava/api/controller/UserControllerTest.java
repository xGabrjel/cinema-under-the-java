package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.configuration.RestAssureConfigurationTestBase;
import com.cinemaUnderTheJava.configuration.support.UserControllerTestSupport;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import org.junit.jupiter.api.Test;

class UserControllerTest extends RestAssureConfigurationTestBase implements UserControllerTestSupport {

    @Test
    void findByUserIdWorksCorrectly() {
        //given
        long userId = 999;

        //when, then
        getUserByIdSupport(userId);
    }

    @Test
    void userAccountActivationWorksCorrectly() {
        //given
        String activationToken = "testToken";

        //when, then
        activateAccountByTokenSupport(activationToken);
    }

    @Test
    void registerUserWorksCorrectly() {
        //given
        UserRequestDto user = DtoFixtures.someUserRequestDto();

        //when, then
        registerUserSupport(user);
    }
}