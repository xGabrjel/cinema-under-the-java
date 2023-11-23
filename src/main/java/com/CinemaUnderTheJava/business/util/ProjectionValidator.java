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

import static com.cinemaUnderTheJava.business.util.ExceptionMessages.*;

@Component
@AllArgsConstructor
public class ProjectionValidator {

    private final ProjectionJpaRepository projectionJpaRepository;

    public void projectionInputValidation(ProjectionRequestDto projectionRequestDto, FilmEntity filmEntity) {
        timeGapValidation(projectionRequestDto, filmEntity);
        correctProjectionTimeValidation(projectionRequestDto);
        totalDailyProjectionsValidation(projectionRequestDto);
    }

    private void timeGapValidation(ProjectionRequestDto projectionRequestDto, FilmEntity filmEntity) {
        List<ProjectionEntity> sameDayProjections = projectionJpaRepository.findByDate(projectionRequestDto.date());

        boolean timeGapTooSmall = sameDayProjections.stream()
                .map(existingProjection -> Duration.between(existingProjection.getTime(), projectionRequestDto.time()).toMinutes())
                .anyMatch(timeDifference -> Math.abs(timeDifference) < filmEntity.getFilmDurationInMinutes() + 20);

        if (timeGapTooSmall) {
            throw new ValidationException(INSUFFICIENT_TIME_GAP.getMessage());
        }
    }

    private void correctProjectionTimeValidation(ProjectionRequestDto projectionRequestDto) {
        if (projectionRequestDto.date().isBefore(LocalDate.now()) ||
                (projectionRequestDto.date().isEqual(LocalDate.now()) &&
                        projectionRequestDto.time().isBefore(LocalTime.now()))) {
            throw new ValidationException(TOO_LATE_TO_SCHEDULE.getMessage());
        }

    }

    private void totalDailyProjectionsValidation(ProjectionRequestDto projectionRequestDto) {
        long size = projectionJpaRepository.findByDate(projectionRequestDto.date())
                .size();

        if (size >= 5) {
            throw new ValidationException(TOO_MANY_PROJECTIONS.getMessage(size));
        }
    }

}
