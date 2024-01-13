package com.cinemaUnderTheJava.fixtures;

import com.cinemaUnderTheJava.database.entity.*;
import com.cinemaUnderTheJava.database.enums.*;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class EntityFixtures {

    public static ProjectionEntity someProjectionEntity() {
        return ProjectionEntity
                .builder()
                .date(LocalDate.of(2034, 10, 10))
                .time(LocalTime.of(10,10))
                .film(someFilmEntity())
                .build();
    }

    public static FilmEntity someFilmEntity() {
        return FilmEntity
                .builder()
                .title("Some title")
                .filmDurationInMinutes(123)
                .category(FilmCategory.HORROR)
                .build();
    }

    public static SeatEntity someSeatEntity() {
        return SeatEntity
                .builder()
                .rowNumber(1)
                .seatInRow(1)
                .status(SeatStatus.AVAILABLE)
                .projection(someProjectionEntity())
                .build();
    }

    public static UserEntity someUserEntity() {
        return UserEntity
                .builder()
                .firstName("First")
                .lastName("Last")
                .email("test123@gmail.com")
                .password("password")
                .role(UserRole.USER)
                .activationStatus(ActivationStatus.ACTIVE)
                .activationToken(null)
                .build();
    }

    public static TicketEntity someTicket() {
        return TicketEntity
                .builder()
                .name(someUserEntity().getFirstName())
                .filmTitle(someFilmEntity().getTitle())
                .projectionDate(someProjectionEntity().getDate())
                .projectionTime(someProjectionEntity().getTime())
                .ticketPrice(BigDecimal.valueOf(10))
                .rowNumber(1)
                .seatInRow(1)
                .roomNumber(1)
                .userId(1L)
                .status(TicketStatus.ACTIVE)
                .ticketType(TicketType.NORMAL)
                .ticketCurrency(TicketCurrency.PLN)
                .build();
    }
}
