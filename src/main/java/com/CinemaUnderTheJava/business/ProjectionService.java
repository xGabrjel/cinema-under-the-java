package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.dto.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.ProjectionResponseDto;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.mapper.ProjectionMapper;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectionService {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final ProjectionMapper projectionMapper;

    public ProjectionEntity saveProjection(ProjectionRequestDto projectionRequestDto) {
        return projectionJpaRepository.save(projectionMapper.dtoToEntity(projectionRequestDto));
    }

    public List<ProjectionResponseDto> getProjectionsByDate(LocalDate date) {
        log.info("Getting projections by date: [%s]".formatted(date.toString()));
        return projectionJpaRepository.findByDate(date)
                .stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }

    public List<ProjectionResponseDto> findAllProjections() {
        log.info("Getting all projections!");
        return projectionJpaRepository.findAll()
                .stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }
}
