package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.configuration.RestAssureConfigurationTestBase;
import com.cinemaUnderTheJava.configuration.support.ProjectionControllerTestSupport;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ProjectionControllerTest extends RestAssureConfigurationTestBase implements ProjectionControllerTestSupport {

    @Test
    void savingProjectionsWorksCorrectly() {
        //given
        long filmId = 3;
        ProjectionRequestDto projectionRequestDto = DtoFixtures.someProjectionRequestDto();

        //when, then
        saveProjectionSupport(filmId, projectionRequestDto);
    }

    @Test
    void gettingProjectionsByDateWorksCorrectly() {
        //given
        LocalDate localDate = LocalDate.of(2024,1, 30);

        //when, then
        getProjectionSupport(localDate);
    }

    @Test
    void gettingAvailableSeatsWorksCorrectly() {
        //given
        long projectionId = 3;

        //when, then
        getAvailableSeatsSupport(projectionId);

    }

    @Test
    void findingAllProjectionsWorksCorrectly() {
        //given, when, then
        findAllSupport();
    }
}