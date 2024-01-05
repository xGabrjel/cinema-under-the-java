package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExchangeRateMapper {

    ExchangeRateResponseDto entityToDto(ExchangeRateEntity exchangeRateEntity);
}
