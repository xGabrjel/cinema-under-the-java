package com.cinemaUnderTheJava.database.mapper;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExchangeRateMapperImplTest {

    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapperImpl();

    @Test
    void shouldMapEntityToDto() {
        //given
        ExchangeRateEntity exchangeRateEntity = EntityFixtures.someExchangeRateEntity()
                .withCurrency("USD")
                .withCode("USD")
                .withMid(BigDecimal.valueOf(1.0));

        //when
        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateMapper.entityToDto(exchangeRateEntity);

        //then
        assertEquals("USD", exchangeRateResponseDto.currency());
        assertEquals("USD", exchangeRateResponseDto.code());
        assertEquals(BigDecimal.valueOf(1.0), exchangeRateResponseDto.mid());
    }

    @Test
    void shouldReturnNullForNullEntity() {
        //given
        ExchangeRateEntity exchangeRateEntity = null;

        //when
        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateMapper.entityToDto(exchangeRateEntity);

        //then
        assertNull(exchangeRateResponseDto);
    }

    @Test
    void shouldMapEntityWithNullFieldsToDto() {
        //given
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();

        //when
        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateMapper.entityToDto(exchangeRateEntity);

        //then
        assertNull(exchangeRateResponseDto.currency());
        assertNull(exchangeRateResponseDto.code());
        assertNull(exchangeRateResponseDto.mid());
    }

    @Test
    void shouldMapEntityWithEmptyStringToDto() {
        //given
        ExchangeRateEntity exchangeRate = EntityFixtures.someExchangeRateEntity()
                .withCurrency("")
                .withCode("")
                .withMid(null);

        //when
        ExchangeRateResponseDto exchangeRateResponseDto = exchangeRateMapper.entityToDto(exchangeRate);

        //then
        assertEquals("", exchangeRateResponseDto.currency());
        assertEquals("", exchangeRateResponseDto.code());
        assertNull(exchangeRateResponseDto.mid());
    }
}