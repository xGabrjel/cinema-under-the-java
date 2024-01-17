package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.SeatEntity;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectionMapperImplTest {

    private final ProjectionMapper projectionMapper = new ProjectionMapperImpl();

    @Test
    void shouldMapDtoToEntity() {
        //given
        ProjectionRequestDto projectionRequestDto = DtoFixtures.someProjectionRequestDto()
                .withDate(LocalDate.of(2022, 1, 1))
                .withTime(LocalTime.of(12, 0));

        //when
        ProjectionEntity projectionEntity = projectionMapper.dtoToEntity(projectionRequestDto);

        //then
        assertEquals(LocalDate.of(2022, 1, 1), projectionEntity.getDate());
        assertEquals(LocalTime.of(12, 0), projectionEntity.getTime());
    }

    @Test
    void shouldReturnNullForNullDto() {
        //given
        ProjectionRequestDto projectionRequestDto = null;

        //when
        ProjectionEntity projectionEntity = projectionMapper.dtoToEntity(projectionRequestDto);

        //then
        assertNull(projectionEntity);
    }

    @Test
    void shouldMapEntityToDto() {
        //given
        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity()
                .withDate(LocalDate.of(2022, 1, 1))
                .withTime(LocalTime.of(12, 0))
                .withFilm(EntityFixtures.someFilmEntity());

        //when
        ProjectionResponseDto projectionResponseDto = projectionMapper.entityToDto(projectionEntity);

        //then
        assertEquals(LocalDate.of(2022, 1, 1), projectionResponseDto.date());
        assertEquals(LocalTime.of(12, 0), projectionResponseDto.time());
        assertNotNull(projectionResponseDto.film());
    }

    @Test
    void shouldReturnNullForNullEntity() {
        //given
        ProjectionEntity projectionEntity = null;

        //when
        ProjectionResponseDto projectionResponseDto = projectionMapper.entityToDto(projectionEntity);

        //then
        assertNull(projectionResponseDto);
    }

    @Test
    void shouldMapProjectionToSeatsDto() {
        //given
        SeatEntity seat1 = new SeatEntity();
        SeatEntity seat2 = new SeatEntity();
        List<SeatEntity> seats = List.of(seat1, seat2);

        ProjectionEntity projectionEntity = EntityFixtures.someProjectionEntity()
                .withSeats(seats);

        //when
        AvailableSeatsDto availableSeatsDto = projectionMapper.projectionToSeatsDto(projectionEntity);

        //then
        assertEquals(seats, availableSeatsDto.seats());
    }

    @Test
    void shouldReturnNullForNullProjection() {
        //given
        ProjectionEntity projectionEntity = null;

        //when
        AvailableSeatsDto availableSeatsDto = projectionMapper.projectionToSeatsDto(projectionEntity);

        //then
        assertNull(availableSeatsDto);
    }
}