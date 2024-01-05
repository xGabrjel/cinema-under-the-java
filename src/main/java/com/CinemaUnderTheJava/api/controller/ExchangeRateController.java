package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.business.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ExchangeRateController.ControllerRoutes.ROOT)
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponseDto>> findAllCurrencies() {
        List<ExchangeRateResponseDto> body = exchangeRateService.findAllCurrency();
        return ResponseEntity.ok(body);
    }

    static final class ControllerRoutes {
        static final String ROOT = "/codes";
    }
}
