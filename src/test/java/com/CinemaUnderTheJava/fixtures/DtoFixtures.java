package com.cinemaUnderTheJava.fixtures;

import com.cinemaUnderTheJava.api.dto.film.FilmRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.api.dto.ticket.TicketReservedDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketStatus;
import com.cinemaUnderTheJava.database.enums.TicketType;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DtoFixtures {

    public static TicketReservationDto someTicketReservationDto(){
        return TicketReservationDto
                .builder()
                .ticketType(TicketType.NORMAL)
                .ticketCurrency(TicketCurrency.PLN)
                .seatInRow(1)
                .rowNumber(1)
                .build();
    }

    public static TicketReservedDto someTicketReservedDto() {
        return TicketReservedDto
                .builder()
                .filmTitle(EntityFixtures.someFilmEntity().getTitle())
                .projectionDate(EntityFixtures.someProjectionEntity().getDate())
                .projectionTime(EntityFixtures.someProjectionEntity().getTime())
                .rowNumber(1)
                .seatInRow(1)
                .ticketStatus(TicketStatus.ACTIVE)
                .build();
    }

    public static UserRequestDto someUserRequestDto() {
        return UserRequestDto
                .builder()
                .firstName(EntityFixtures.someUserEntity().getFirstName())
                .lastName(EntityFixtures.someUserEntity().getLastName())
                .email(EntityFixtures.someUserEntity().getEmail())
                .password(EntityFixtures.someUserEntity().getPassword())
                .repeatedPassword(EntityFixtures.someUserEntity().getPassword())
                .role(EntityFixtures.someUserEntity().getRole())
                .build();
    }

    public static ProjectionRequestDto someProjectionRequestDto() {
        return ProjectionRequestDto
                .builder()
                .date(LocalDate.of(2030,10,10))
                .time(LocalTime.of(10, 10))
                .build();
    }

    public static FilmRequestDto someFilmRequestDto() {
        return FilmRequestDto
                .builder()
                .title("Test")
                .category(FilmCategory.HORROR)
                .filmDurationInMinutes(123)
                .build();
    }
}
