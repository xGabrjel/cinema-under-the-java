package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.configuration.RestAssureConfigurationTestBase;
import com.cinemaUnderTheJava.configuration.support.ExchangeRateControllerTestSupport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeRateControllerTest extends RestAssureConfigurationTestBase implements ExchangeRateControllerTestSupport {

    @Test
    void findAllCurrenciesWorksCorrectly() {
        //given, when
        List<ExchangeRateResponseDto> codes = getExchangeRatesSupport();

        //then
        assertThat(codes).isNotNull();
        assertThat(codes.get(0)).hasFieldOrPropertyWithValue("currency", "bat (Tajlandia)");
        assertThat(codes.get(0)).hasFieldOrPropertyWithValue("code", "THB");
    }
}