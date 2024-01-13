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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

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
        Assertions.assertNotNull(allFilms);
        Assertions.assertNotEquals(Collections.emptyList(), allFilms);
        Assertions.assertEquals("The Shawshank Redemption", allFilms.get(0).title());
        Assertions.assertEquals(FilmCategory.DRAMA, allFilms.get(0).category());
        Assertions.assertEquals(142, allFilms.get(0).filmDurationInMinutes());
    }

    @Test
    void getFilmByCategoryWorksCorrectly() {
        //given
        FilmCategory horror = FilmCategory.HORROR;

        //when
        List<FilmResponseDto> result = filmService.getFilmByCategory(horror);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(Collections.emptyList(), result);
        Assertions.assertEquals(horror, result.get(0).category());
    }

    @Test
    void saveNewFilmWorksCorrectly() {
        //given
        FilmRequestDto filmRequestDto = DtoFixtures.someFilmRequestDto();

        //when
        FilmEntity result = filmService.saveNewFilm(filmRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(filmRequestDto.category(), result.getCategory());
        Assertions.assertEquals(filmRequestDto.title(), result.getTitle());
        Assertions.assertEquals(filmRequestDto.filmDurationInMinutes(), result.getFilmDurationInMinutes());
    }

    @Test
    void saveNewFilmThrowExceptionWhenDuplicateFilmOccurred() {
        //given
        FilmRequestDto alreadySavedFilm = DtoFixtures.someFilmRequestDto()
                .withTitle("The Dark Knight")
                .withCategory(FilmCategory.ACTION)
                .withFilmDurationInMinutes(152);

        //when, then
        Assertions.assertThrows(DuplicateException.class, () -> filmService.saveNewFilm(alreadySavedFilm));
    }

    @Test
    void getFilmDtoByIdWorksCorrectly() {
        //given
        long filmId = 1L;

        //when
        FilmResponseDto result = filmService.getFilmDtoById(filmId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("The Shawshank Redemption", result.title());
        Assertions.assertEquals(FilmCategory.DRAMA, result.category());
        Assertions.assertEquals(142, result.filmDurationInMinutes());
    }

    @Test
    void getFilmEntityWorksCorrectly() {
        //given
        long filmId = 1L;

        //when
        FilmEntity result = filmService.getFilmEntityById(filmId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("The Shawshank Redemption", result.getTitle());
        Assertions.assertEquals(FilmCategory.DRAMA, result.getCategory());
        Assertions.assertEquals(142, result.getFilmDurationInMinutes());
    }

    @Test
    void getFilmDtoByIdThrowsExceptionCorrectly() {
        //given, when, then
        Assertions.assertThrows(NotFoundException.class, () -> filmService.getFilmDtoById(999L));
    }

    @Test
    void getFilmEntityThrowsExceptionCorrectly() {
        //given, when, then
        Assertions.assertThrows(NotFoundException.class, () -> filmService.getFilmEntityById(999L));
    }

    @Test
    void deleteFilmWorksCorrectly() {
        //given
        long filmId = 2L;

        //when
        filmService.deleteFilm(filmId);

        //then
        Assertions.assertThrows(NotFoundException.class, () -> filmService.getFilmDtoById(filmId));
    }
}