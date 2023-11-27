package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.NewUserDto;
import com.cinemaUnderTheJava.api.dto.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.UserResponseDto;
import com.cinemaUnderTheJava.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity dtoToEntity(UserRequestDto userRequestDto);

    UserResponseDto entityToDto(UserEntity userEntity);

    NewUserDto newUserEntityToDto(UserEntity userEntity);
}
