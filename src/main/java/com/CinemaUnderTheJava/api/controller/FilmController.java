package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.film.FilmResponseDto;
import com.cinemaUnderTheJava.business.FilmService;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cinemaUnderTheJava.api.controller.FilmController.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class FilmController {

    static final String ROOT = "/films";
    static final String CATEGORY = "/category";
    static final String SAVE_NEW_FILM = "/newFilm";
    static final String DELETE_FILM_BY_ID = "/delete/{id}";
    static final String GET_BY_ID = "/{id}";

    static final String GET_ALL_FILMS_MESSAGE = "Get all films";
    static final String GET_FILM_BY_CATEGORY_MESSAGE = "Get films by category";
    static final String SAVE_NEW_FILM_MESSAGE = "Save a new film";
    static final String DELETE_FILM_BY_ID_MESSAGE = "Delete a film by ID";
    static final String GET_BY_ID_MESSAGE = "Get a film by ID";

    private final FilmService filmService;

    @GetMapping
    @Operation(summary = GET_ALL_FILMS_MESSAGE)
    public ResponseEntity<List<FilmResponseDto>> getAllFilms() {
        List<FilmResponseDto> all = filmService.getAllFilms();
        return ResponseEntity.ok(all);
    }

    @GetMapping(CATEGORY)
    @Operation(summary = GET_FILM_BY_CATEGORY_MESSAGE)
    public ResponseEntity<List<FilmResponseDto>> getFilmByCategory(
            @RequestParam("category") FilmCategory filmCategory
    ) {
        List<FilmResponseDto> filmsByCategory = filmService.getFilmByCategory(filmCategory);
        return ResponseEntity.ok(filmsByCategory);
    }

    @PostMapping(SAVE_NEW_FILM)
    @Operation(summary = SAVE_NEW_FILM_MESSAGE)
    public ResponseEntity<FilmEntity> saveNewFilm(
            @RequestBody FilmRequestDto filmRequestDto
    ) {
        return new ResponseEntity<>(filmService.saveNewFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_FILM_BY_ID)
    @Operation(summary = DELETE_FILM_BY_ID_MESSAGE)
    public ResponseEntity<Void> deleteFilm(
            @PathVariable Long id
    ) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(GET_BY_ID)
    @Operation(summary = GET_BY_ID_MESSAGE)
    public ResponseEntity<FilmResponseDto> getFilmById(
            @PathVariable Long id
    ) {
        FilmResponseDto filmResponseDto = filmService.getFilmDtoById(id);
        return ResponseEntity.ok(filmResponseDto);
    }
}
