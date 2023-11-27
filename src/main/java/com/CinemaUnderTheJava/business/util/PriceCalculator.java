package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.enums.TicketType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import static com.cinemaUnderTheJava.business.util.PriceCalculator.TicketPrice.*;

@Component
@AllArgsConstructor
public class PriceCalculator {

    public BigDecimal calculatePriceForProjection(ProjectionEntity projection, TicketReservationDto ticketReservationDto) {
        return BigDecimal.valueOf(verifyStudentDiscount(projection, ticketReservationDto));
    }

    private int wednesdayDiscount(ProjectionEntity projection) {
        return (projection.getDate().getDayOfWeek() == DayOfWeek.WEDNESDAY) ? WEDNESDAY_DISCOUNT : STANDARD_PRICE;
    }

    private int verifyStudentDiscount(ProjectionEntity projection, TicketReservationDto ticketReservationDto) {
        return (ticketReservationDto.ticketType() == TicketType.DISCOUNTED) ?
                wednesdayDiscount(projection) - STUDENT_DISCOUNT : wednesdayDiscount(projection);
    }

    static final class TicketPrice {
        static final int STANDARD_PRICE = 30;
        static final int WEDNESDAY_DISCOUNT = 20;
        static final int STUDENT_DISCOUNT = 5;
    }
}
