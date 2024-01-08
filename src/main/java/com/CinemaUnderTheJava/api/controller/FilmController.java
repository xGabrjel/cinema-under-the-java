package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.film.FilmResponseDto;
import com.cinemaUnderTheJava.business.FilmService;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cinemaUnderTheJava.api.controller.FilmController.ControllerRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<FilmResponseDto>> getAllFilms() {
        List<FilmResponseDto> all = filmService.getAllFilms();
        return ResponseEntity.ok(all);
    }

    @GetMapping(CATEGORY)
    public ResponseEntity<List<FilmResponseDto>> getFilmByCategory(
            @RequestParam("category") FilmCategory filmCategory
    ) {
        List<FilmResponseDto> filmsByCategory = filmService.getFilmByCategory(filmCategory);
        return ResponseEntity.ok(filmsByCategory);
    }

    @PostMapping(SAVE_NEW_FILM)
    public ResponseEntity<FilmEntity> saveNewFilm(
            @RequestBody FilmRequestDto filmRequestDto
    ) {
        return new ResponseEntity<>(filmService.saveNewFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_FILM_BY_ID)
    public ResponseEntity<Void> deleteFilm(
            @PathVariable Long id
    ) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<FilmResponseDto> getFilmById(
            @PathVariable Long id
    ) {
        FilmResponseDto filmResponseDto = filmService.getFilmById(id);
        return ResponseEntity.ok(filmResponseDto);
    }

    static final class ControllerRoutes {
        static final String ROOT = "/films";
        static final String CATEGORY = "/category";
        static final String SAVE_NEW_FILM = "/newFilm";
        static final String DELETE_FILM_BY_ID = "/delete/{id}";
        static final String GET_BY_ID = "/{id}";
    }
}
