package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.TicketReservedDto;
import com.cinemaUnderTheJava.database.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    TicketReservedDto entityToDto(TicketEntity ticket);
}
