package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.DuplicateException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.film.FilmResponseDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import com.cinemaUnderTheJava.database.mapper.FilmMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.DUPLICATE_FILM;
import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.FILM_NOT_FOUND;


@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private final FilmJpaRepository filmJpaRepository;
    private final FilmMapper filmMapper;

    public List<FilmResponseDto> getAllFilms() {
        log.info("Returning all films...");
        List<FilmEntity> all = filmJpaRepository.findAll();
        log.info("Found [%s] films".formatted(all.size()));

        return all.stream()
                .map(filmMapper::entityToDto)
                .toList();
    }

    public List<FilmResponseDto> getFilmByCategory(FilmCategory filmCategory) {
        log.info("Getting film by category: [%s]".formatted(filmCategory));
        List<FilmEntity> filmByCategory = filmJpaRepository.findByCategory(filmCategory);
        log.info("Found [%s] films of [%s] category".formatted(filmByCategory.size(), filmCategory));

        return filmByCategory.stream()
                .map(filmMapper::entityToDto)
                .toList();
    }

    @Transactional
    public FilmEntity saveNewFilm(FilmRequestDto filmRequestDto) {
        filmJpaRepository.findByTitle(filmRequestDto.title()).ifPresent(existingFilm -> {
            throw new DuplicateException(DUPLICATE_FILM.getMessage(existingFilm.getTitle()));
        });

        log.info("New film is saved: [%s]".formatted(filmRequestDto));
        return filmJpaRepository.save(filmMapper.dtoToEntity(filmRequestDto));
    }

    @Transactional
    public void deleteFilm(Long id) {
        log.info("Deleting film with id: [%s]".formatted(id));
        FilmEntity film = getFilmById(id);

        filmJpaRepository.delete(film);
        log.info("Film with id: [%s], was deleted successfully!".formatted(id));
    }

    private FilmEntity getFilmById(Long id) {
        return filmJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND.getMessage(id)));
    }
}
