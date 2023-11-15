package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.dto.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.FilmResponseDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.mapper.FilmMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import com.cinemaUnderTheJava.database.util.FilmCategory;
import com.cinemaUnderTheJava.database.util.exceptions.DuplicateFilmException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new DuplicateFilmException(existingFilm.getTitle());
        });

        log.info("New film is saved: [%s]".formatted(filmRequestDto));
        return filmJpaRepository.save(filmMapper.dtoToEntity(filmRequestDto));
    }

    @Transactional
    public void deleteFilm(Long id) {
        log.info("Deleting film with id: [%s]".formatted(id));
        FilmEntity film = filmJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such film with id: [%s]".formatted(id)));

        filmJpaRepository.delete(film);
        log.info("Film with id: [%s], was deleted successfully!".formatted(id));
    }
}
