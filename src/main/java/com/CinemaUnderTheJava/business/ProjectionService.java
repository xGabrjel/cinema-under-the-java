package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.dto.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.ProjectionResponseDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.mapper.ProjectionMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import com.cinemaUnderTheJava.database.util.exceptions.FilmNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectionService {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final ProjectionMapper projectionMapper;
    private final FilmJpaRepository filmJpaRepository;

    @Transactional
    public ProjectionEntity saveProjection(ProjectionRequestDto projectionRequestDto, Long filmId) {
        log.info("Saving projection: [%s]".formatted(projectionRequestDto));

        FilmEntity filmEntity = filmJpaRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));

        ProjectionEntity projectionEntity = projectionMapper.dtoToEntity(projectionRequestDto);
        projectionEntity.setFilm(filmEntity);
        ProjectionEntity savedProjection = projectionJpaRepository.save(projectionEntity);

        log.info("Projection saved successfully: Id: [%s], Date: [%s], Time: [%s]"
                .formatted(
                        savedProjection.getId(),
                        savedProjection.getDate(),
                        savedProjection.getTime()));

        return savedProjection;
    }

    public List<ProjectionResponseDto> getProjectionsByDate(LocalDate date) {
        log.info("Getting projections by date: [%s]".formatted(date.toString()));
        List<ProjectionEntity> projectionByDate = projectionJpaRepository.findByDate(date);

        return projectionByDate.stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }

    public List<ProjectionResponseDto> findAllProjections() {
        log.info("Getting all projections!");
        List<ProjectionEntity> allProjections = projectionJpaRepository.findAll();

        return allProjections.stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }
}
