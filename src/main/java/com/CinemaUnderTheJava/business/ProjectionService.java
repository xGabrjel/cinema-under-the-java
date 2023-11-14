package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.dto.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.ProjectionResponseDto;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.mapper.ProjectionMapper;
import com.cinemaUnderTheJava.database.repository.jpa.ProjectionJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectionService {

    private final ProjectionJpaRepository projectionJpaRepository;
    private final ProjectionMapper projectionMapper;

    public ProjectionEntity saveProjection(ProjectionRequestDto projectionRequestDto) {
        return projectionJpaRepository.save(projectionMapper.dtoToEntity(projectionRequestDto));
    }

    public List<ProjectionResponseDto> getProjectionsByDate(LocalDate date) {
        return projectionJpaRepository.findByDate(date)
                .stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }

    public List<ProjectionResponseDto> findAllProjections() {
        return projectionJpaRepository.findAll()
                .stream()
                .map(projectionMapper::entityToDto)
                .toList();
    }
}
