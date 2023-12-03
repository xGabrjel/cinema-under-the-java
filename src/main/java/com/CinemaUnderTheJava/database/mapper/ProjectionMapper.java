package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectionMapper {

    ProjectionEntity dtoToEntity(ProjectionRequestDto projectionRequestDto);

    ProjectionResponseDto entityToDto(ProjectionEntity projectionEntity);

    AvailableSeatsDto projectionToSeatsDto(ProjectionEntity projection);
}
