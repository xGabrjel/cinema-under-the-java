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

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/all")
    public ResponseEntity<List<FilmResponseDto>> getAllFilms() {
        List<FilmResponseDto> all = filmService.getAllFilms();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @GetMapping("/category")
    public ResponseEntity<List<FilmResponseDto>> getFilmByCategory(
            @RequestParam("category") FilmCategory filmCategory
    ) {
        List<FilmResponseDto> filmsByCategory = filmService.getFilmByCategory(filmCategory);
        return ResponseEntity.status(HttpStatus.OK).body(filmsByCategory);
    }

    @PostMapping("/add")
    public ResponseEntity<FilmEntity> saveNewFilm(
            @RequestBody FilmRequestDto filmRequestDto
    ) {
        return new ResponseEntity<>(filmService.saveNewFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFilm(
            @PathVariable Long id
    ) {
        filmService.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
