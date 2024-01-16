package com.cinemaUnderTheJava.configuration.support;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ExchangeRateControllerTestSupport {

    RequestSpecification requestSpecification();

    default List<ExchangeRateResponseDto> getExchangeRatesSupport() {
        return requestSpecification()
                .get("/codes")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", ExchangeRateResponseDto.class);
    }
}
