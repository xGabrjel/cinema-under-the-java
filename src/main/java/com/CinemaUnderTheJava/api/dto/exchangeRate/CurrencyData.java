package com.cinemaUnderTheJava.api.dto.exchangeRate;

import java.util.List;

public record CurrencyData(
        String table,
        String no,
        String effectiveDate,
        List<Rate> rates
) {
}
