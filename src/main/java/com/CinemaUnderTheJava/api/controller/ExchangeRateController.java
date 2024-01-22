package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.business.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cinemaUnderTheJava.api.controller.ExchangeRateController.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class ExchangeRateController {

    static final String ROOT = "/codes";
    static final String FIND_ALL_CURRENCIES_MESSAGE = "Get all current exchange rates from the NBP - National Bank of Poland";

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    @Operation(summary = FIND_ALL_CURRENCIES_MESSAGE)
    public ResponseEntity<List<ExchangeRateResponseDto>> findAllCurrencies() {
        List<ExchangeRateResponseDto> body = exchangeRateService.findAllCurrency();
        return ResponseEntity.ok(body);
    }
}
