package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.business.util.ProjectionValidatorUtil;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.SeatEntity;
import com.cinemaUnderTheJava.database.enums.SeatStatus;
import com.cinemaUnderTheJava.database.mapper.ProjectionMapper;
import com.cinemaUnderTheJava.database.repository.jpa.FilmJpaRepository;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.*;
import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectionService {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final ProjectionMapper projectionMapper;
    private final FilmJpaRepository filmJpaRepository;
    private final ProjectionValidatorUtil projectionValidator;
    private final SeatService seatService;

    @Transactional
    public ProjectionResponseDto saveProjection(ProjectionRequestDto projectionRequestDto, Long filmId) {
        log.info("Saving projection: [%s]".formatted(projectionRequestDto));
        FilmEntity filmEntity = getFilmById(filmId);
        projectionValidator.projectionInputValidation(projectionRequestDto, filmEntity);

        ProjectionEntity projectionEntity = projectionMapper.dtoToEntity(projectionRequestDto)
                .withFilm(filmEntity)
                .withSeats(generateNewSeats());

        ProjectionEntity savedProjection = projectionJpaRepository.save(projectionEntity);

        log.info("Projection saved successfully: Id: [%s], Date: [%s], Time: [%s]"
                .formatted(
                        savedProjection.getId(),
                        savedProjection.getDate(),
                        savedProjection.getTime()));
        return projectionMapper.entityToDto(savedProjection);
    }

    public List<ProjectionResponseDto> getProjectionsByDate(LocalDate date) {
        List<ProjectionEntity> projectionByDate = projectionJpaRepository.findByDate(date);

        if (projectionByDate.isEmpty()) {
            throw new NotFoundException(PROJECTIONS_NOT_FOUND_WITH_DATE.getMessage());
        }

        log.info("Getting projections by date: [%s]".formatted(date.toString()));
        return projectionByDate.stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }

    public List<ProjectionResponseDto> getAllProjections() {
        List<ProjectionEntity> allProjections = projectionJpaRepository.findAll();

        log.info("Getting all projections!");
        return allProjections.stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }

    private FilmEntity getFilmById(Long filmId) {
        return filmJpaRepository.findById(filmId)
                .orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND.getMessage(filmId)));
    }

    @Transactional
    private List<SeatEntity> generateNewSeats() {
        final int NUMBER_OF_ROWS = 10;
        final int SEATS_IN_ROW = 10;

        log.info("Generating new seats!");
        return rangeClosed(1, NUMBER_OF_ROWS)
                .boxed()
                .flatMap(row ->
                        rangeClosed(1, SEATS_IN_ROW)
                                .mapToObj(seat -> seatService.generateSeat(row, seat, SeatStatus.AVAILABLE)))
                .toList();
    }

    public AvailableSeatsDto getAvailableSeats(Long projectionId) {
        ProjectionEntity projection = projectionJpaRepository.findById(projectionId)
                .orElseThrow(() -> new NotFoundException(PROJECTION_NOT_FOUND.getMessage(projectionId)));

        log.info("Getting available seats");
        return projectionMapper.projectionToSeatsDto(projection);
    }
}
