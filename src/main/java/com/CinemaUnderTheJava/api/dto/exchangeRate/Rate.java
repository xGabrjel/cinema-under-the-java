package com.cinemaUnderTheJava.api.dto.exchangeRate;

import java.math.BigDecimal;

public record Rate(
        String currency,
        String code,
        BigDecimal mid
) {
}
