package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.DuplicateException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.film.FilmResponseDto;
import com.cinemaUnderTheJava.configuration.AbstractIT;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import com.cinemaUnderTheJava.database.mapper.FilmMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class FilmServiceTest extends AbstractIT {


    private final FilmJpaRepository filmJpaRepository;
    private final FilmMapper filmMapper;
    private final FilmService filmService;

    @Test
    void getAllFilmsShouldReturnAllAvailableFilms() {
        //given, when
        List<FilmResponseDto> allFilms = filmService.getAllFilms();

        //then
        assertNotNull(allFilms);
        assertNotEquals(Collections.emptyList(), allFilms);
        assertEquals("The Shawshank Redemption", allFilms.get(0).title());
        assertEquals(FilmCategory.DRAMA, allFilms.get(0).category());
        assertEquals(142, allFilms.get(0).filmDurationInMinutes());
    }

    @Test
    void getFilmByCategoryWorksCorrectly() {
        //given
        FilmCategory horror = FilmCategory.HORROR;

        //when
        List<FilmResponseDto> result = filmService.getFilmByCategory(horror);

        //then
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(horror, result.get(0).category());
    }

    @Test
    void saveNewFilmWorksCorrectly() {
        //given
        FilmRequestDto filmRequestDto = DtoFixtures.someFilmRequestDto();

        //when
        FilmEntity result = filmService.saveNewFilm(filmRequestDto);

        //then
        assertNotNull(result);
        assertEquals(filmRequestDto.category(), result.getCategory());
        assertEquals(filmRequestDto.title(), result.getTitle());
        assertEquals(filmRequestDto.filmDurationInMinutes(), result.getFilmDurationInMinutes());
    }

    @Test
    void saveNewFilmThrowExceptionWhenDuplicateFilmOccurred() {
        //given
        FilmRequestDto alreadySavedFilm = DtoFixtures.someFilmRequestDto()
                .withTitle("The Dark Knight")
                .withCategory(FilmCategory.ACTION)
                .withFilmDurationInMinutes(152);

        //when, then
        assertThrows(DuplicateException.class, () -> filmService.saveNewFilm(alreadySavedFilm));
    }

    @Test
    void getFilmDtoByIdWorksCorrectly() {
        //given
        long filmId = 1L;

        //when
        FilmResponseDto result = filmService.getFilmDtoById(filmId);

        //then
        assertNotNull(result);
        assertEquals("The Shawshank Redemption", result.title());
        assertEquals(FilmCategory.DRAMA, result.category());
        assertEquals(142, result.filmDurationInMinutes());
    }

    @Test
    void getFilmEntityWorksCorrectly() {
        //given
        long filmId = 1L;

        //when
        FilmEntity result = filmService.getFilmEntityById(filmId);

        //then
        assertNotNull(result);
        assertEquals("The Shawshank Redemption", result.getTitle());
        assertEquals(FilmCategory.DRAMA, result.getCategory());
        assertEquals(142, result.getFilmDurationInMinutes());
    }

    @Test
    void getFilmDtoByIdThrowsExceptionCorrectly() {
        //given, when, then
        assertThrows(NotFoundException.class, () -> filmService.getFilmDtoById(999L));
    }

    @Test
    void getFilmEntityThrowsExceptionCorrectly() {
        //given, when, then
        assertThrows(NotFoundException.class, () -> filmService.getFilmEntityById(999L));
    }

    @Test
    void deleteFilmWorksCorrectly() {
        //given
        long filmId = 2L;

        //when
        filmService.deleteFilm(filmId);

        //then
        assertThrows(NotFoundException.class, () -> filmService.getFilmDtoById(filmId));
    }
}