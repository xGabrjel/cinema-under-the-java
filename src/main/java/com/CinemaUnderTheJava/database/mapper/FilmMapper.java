package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.FilmResponseDto;
import com.cinemaUnderTheJava.database.entity.FilmEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FilmMapper {

    FilmResponseDto entityToDto(FilmEntity filmEntity);

    FilmEntity dtoToEntity(FilmRequestDto filmRequestDto);
}
