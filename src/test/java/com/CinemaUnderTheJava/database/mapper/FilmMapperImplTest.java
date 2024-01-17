package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.film.FilmResponseDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilmMapperImplTest {

    private final FilmMapper filmMapper = new FilmMapperImpl();

    @Test
    void shouldMapEntityToDto() {
        //given
        FilmEntity filmEntity = EntityFixtures.someFilmEntity()
                .withTitle("Inception")
                .withCategory(FilmCategory.ACTION)
                .withFilmDurationInMinutes(150);

        //when
        FilmResponseDto filmResponseDto = filmMapper.entityToDto(filmEntity);

        //then
        assertEquals("Inception", filmResponseDto.title());
        assertEquals(FilmCategory.ACTION, filmResponseDto.category());
        assertEquals(150, filmResponseDto.filmDurationInMinutes());
    }

    @Test
    void shouldReturnNullForNullEntity() {
        //given
        FilmEntity filmEntity = null;

        //when
        FilmResponseDto filmResponseDto = filmMapper.entityToDto(filmEntity);

        //then
        assertNull(filmResponseDto);
    }

    @Test
    void shouldMapDtoToEntity() {
        //given
        FilmRequestDto filmRequestDto = DtoFixtures.someFilmRequestDto()
                .withTitle("Interstellar")
                .withCategory(FilmCategory.DRAMA)
                .withFilmDurationInMinutes(180);
        //when
        FilmEntity filmEntity = filmMapper.dtoToEntity(filmRequestDto);

        //then
        assertEquals("Interstellar", filmEntity.getTitle());
        assertEquals(FilmCategory.DRAMA, filmEntity.getCategory());
        assertEquals(180, filmEntity.getFilmDurationInMinutes());
    }

    @Test
    void shouldReturnNullForNullDto() {
        //given
        FilmRequestDto filmRequestDto = null;

        //when
        FilmEntity filmEntity = filmMapper.dtoToEntity(filmRequestDto);

        //then
        assertNull(filmEntity);
    }
}