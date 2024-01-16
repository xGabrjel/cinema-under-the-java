package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.api.dto.ticket.TicketReservationDto;
import com.cinemaUnderTheJava.business.ExchangeRateService;
import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketType;
import com.cinemaUnderTheJava.database.repository.jpa.ExchangeRateJpaRepository;
import com.cinemaUnderTheJava.fixtures.DtoFixtures;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorUtilTest {

    @Mock
    private ExchangeRateJpaRepository exchangeRateJpaRepository;

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private PriceCalculatorUtil priceCalculatorUtil;

    @Test
    void calculatePriceForStudentOnWednesday() {
        //given
        ProjectionEntity projection = EntityFixtures.someProjectionEntity()
                .withDate(LocalDate.of(2024, 1, 17));

        TicketReservationDto ticket = DtoFixtures.someTicketReservationDto()
                .withTicketType(TicketType.DISCOUNTED)
                .withTicketCurrency(TicketCurrency.PLN);

        ExchangeRateEntity exchangeRateEntity = EntityFixtures.someExchangeRateEntity();

        BigDecimal expectedPrice = BigDecimal.valueOf(15).divide(BigDecimal.valueOf(1), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);

        //when
        when(exchangeRateJpaRepository.findByCode("PLN")).thenReturn(exchangeRateEntity);

        BigDecimal result = priceCalculatorUtil.calculatePriceForProjection(projection, ticket);

        //then
        assertEquals(expectedPrice, result);
    }

    @Test
    void calculatePriceForNonStudentOnWednesday() {
        //given
        ProjectionEntity projection = EntityFixtures.someProjectionEntity()
                .withDate(LocalDate.of(2024, 1, 17));

        TicketReservationDto ticket = DtoFixtures.someTicketReservationDto()
                .withTicketType(TicketType.NORMAL)
                .withTicketCurrency(TicketCurrency.PLN);

        ExchangeRateEntity exchangeRateEntity = EntityFixtures.someExchangeRateEntity();

        BigDecimal expectedPrice = BigDecimal.valueOf(20).divide(BigDecimal.valueOf(1), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);

        //when
        when(exchangeRateJpaRepository.findByCode("PLN")).thenReturn(exchangeRateEntity);

        BigDecimal result = priceCalculatorUtil.calculatePriceForProjection(projection, ticket);

        //then
        assertEquals(expectedPrice, result);
    }

    @Test
    void calculatePriceForStudentOnFriday() {
        //given
        ProjectionEntity projection = EntityFixtures.someProjectionEntity()
                .withDate(LocalDate.of(2024, 1, 19));

        TicketReservationDto ticket = DtoFixtures.someTicketReservationDto()
                .withTicketType(TicketType.DISCOUNTED)
                .withTicketCurrency(TicketCurrency.PLN);

        ExchangeRateEntity exchangeRateEntity = EntityFixtures.someExchangeRateEntity();

        BigDecimal expectedPrice = BigDecimal.valueOf(25).divide(BigDecimal.valueOf(1), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);

        //when
        when(exchangeRateJpaRepository.findByCode("PLN")).thenReturn(exchangeRateEntity);

        BigDecimal result = priceCalculatorUtil.calculatePriceForProjection(projection, ticket);

        //then
        assertEquals(expectedPrice, result);
    }

    @Test
    void calculatePriceForNonStudentOnFriday() {
        //given
        ProjectionEntity projection = EntityFixtures.someProjectionEntity()
                .withDate(LocalDate.of(2024, 1, 19));

        TicketReservationDto ticket = DtoFixtures.someTicketReservationDto()
                .withTicketType(TicketType.NORMAL)
                .withTicketCurrency(TicketCurrency.PLN);

        ExchangeRateEntity exchangeRateEntity = EntityFixtures.someExchangeRateEntity();

        BigDecimal expectedPrice = BigDecimal.valueOf(30).divide(BigDecimal.valueOf(1), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);

        //when
        when(exchangeRateJpaRepository.findByCode("PLN")).thenReturn(exchangeRateEntity);

        BigDecimal result = priceCalculatorUtil.calculatePriceForProjection(projection, ticket);

        //then
        assertEquals(expectedPrice, result);
    }
}