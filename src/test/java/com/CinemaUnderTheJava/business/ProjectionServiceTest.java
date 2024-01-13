package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.business.util.ProjectionValidatorUtil;
import com.cinemaUnderTheJava.configuration.AbstractIT;
import com.cinemaUnderTheJava.database.mapper.ProjectionMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class ProjectionServiceTest extends AbstractIT {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final ProjectionMapper projectionMapper;
    private final FilmJpaRepository filmJpaRepository;
    private final ProjectionValidatorUtil projectionValidator;
    private final SeatService seatService;
    private final ProjectionService projectionService;

    @Test
    void saveProjectionWorkCorrectly() {
        //given
        ProjectionRequestDto projectionRequestDto = DtoFixtures.someProjectionRequestDto();
        long longId = 1;

        //when
        ProjectionResponseDto savedProjection = projectionService.saveProjection(projectionRequestDto, longId);

        //then
        Assertions.assertNotNull(savedProjection);
        Assertions.assertEquals(projectionRequestDto.date(), savedProjection.date());
        Assertions.assertEquals(projectionRequestDto.time(), savedProjection.time());
    }

    @Test
    void getProjectionsByDateWorksCorrectly() {
        //given
        LocalDate localDate = LocalDate.of(2024,1,15);

        //when
        List<ProjectionResponseDto> result = projectionService.getProjectionsByDate(localDate);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(Collections.emptyList(), result);
        Assertions.assertEquals(localDate, result.get(0).date());
        Assertions.assertEquals(localDate, result.get(1).date());
    }

    @Test
    void getProjectionsByDateThrowsExceptionCorrectly() {
        //given
        LocalDate localDate = LocalDate.of(3024,1,15);

        //when, then
        Assertions.assertThrows(NotFoundException.class, () -> projectionService.getProjectionsByDate(localDate));
    }

    @Test
    void getAllProjectionsWorksCorrectly() {
        //given, when
        List<ProjectionResponseDto> result = projectionService.getAllProjections();

        //then
        Assertions.assertNotEquals(Collections.emptyList(), result);
    }

    @Test
    void getAvailableSeatsWorksCorrectly() {
        //given
        long projectionId = 1;

        //when
        AvailableSeatsDto result = projectionService.getAvailableSeats(projectionId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(Collections.emptyList(), result.seats());
    }

    @Test
    void getAvailableSeatsThrowsExceptionCorrectly() {
        //given
        long projectionId = 999;

        //when, then
        Assertions.assertThrows(NotFoundException.class, () -> projectionService.getAvailableSeats(projectionId));
    }
}