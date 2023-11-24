package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.api.dto.ProjectionRequestDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.*;

@Component
@AllArgsConstructor
public class ProjectionValidator {

    private final ProjectionJpaRepository projectionJpaRepository;

    public void projectionInputValidation(ProjectionRequestDto projection, FilmEntity film) {
        timeGapValidation(projection, film);
        correctProjectionTimeValidation(projection);
        totalDailyProjectionsValidation(projection);
    }

    private void timeGapValidation(ProjectionRequestDto projection, FilmEntity film) {
        List<ProjectionEntity> sameDayProjections = projectionJpaRepository.findByDate(projection.date());

        boolean timeGapTooSmall = sameDayProjections.stream()
                .map(existingProjection -> Duration.between(existingProjection.getTime(), projection.time()).toMinutes())
                .anyMatch(timeDifference -> Math.abs(timeDifference) < film.getFilmDurationInMinutes() + 20);

        if (timeGapTooSmall) {
            throw new ValidationException(INSUFFICIENT_TIME_GAP.getMessage());
        }
    }

    private void correctProjectionTimeValidation(ProjectionRequestDto projection) {
        if (projection.date().isBefore(LocalDate.now()) ||
                (projection.date().isEqual(LocalDate.now()) &&
                        projection.time().isBefore(LocalTime.now()))) {
            throw new ValidationException(TOO_LATE_TO_SCHEDULE.getMessage());
        }
    }

    private void totalDailyProjectionsValidation(ProjectionRequestDto projection) {
        long size = projectionJpaRepository.findByDate(projection.date())
                .size();

        if (size >= 5) {
            throw new ValidationException(TOO_MANY_PROJECTIONS.getMessage(size));
        }
    }
}
