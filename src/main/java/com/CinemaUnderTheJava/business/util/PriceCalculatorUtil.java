package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.enums.TicketType;
import com.cinemaUnderTheJava.database.repository.jpa.ExchangeRateJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;

import static com.cinemaUnderTheJava.business.util.PriceCalculatorUtil.TicketPrice.*;

@Component
@AllArgsConstructor
public class PriceCalculatorUtil {

    private final ExchangeRateJpaRepository exchangeRateJpaRepository;

    public BigDecimal calculatePriceForProjection(ProjectionEntity projection, TicketReservationDto ticketReservationDto) {
        ExchangeRateEntity exchangeRate = exchangeRateJpaRepository.findByCode(ticketReservationDto.ticketCurrency().toString());
        BigDecimal finalPrice = BigDecimal.valueOf(verifyStudentDiscount(projection, ticketReservationDto)).divide(exchangeRate.getMid(), RoundingMode.HALF_UP);
        return finalPrice.setScale(1, RoundingMode.HALF_UP);
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
