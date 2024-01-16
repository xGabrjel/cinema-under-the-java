package com.cinemaUnderTheJava.api.dto.exchangeRate;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(
        String currency,
        String code,
        BigDecimal mid
) {
}
