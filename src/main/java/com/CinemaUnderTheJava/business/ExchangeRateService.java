package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.dto.exchangeRate.CurrencyData;
import com.cinemaUnderTheJava.api.dto.exchangeRate.ExchangeRateResponseDto;
import com.cinemaUnderTheJava.api.dto.exchangeRate.Rate;
import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import com.cinemaUnderTheJava.database.mapper.ExchangeRateMapper;
import com.cinemaUnderTheJava.database.repository.jpa.ExchangeRateJpaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final WebClient webClient;
    private final ExchangeRateJpaRepository exchangeRateJpaRepository;
    private final ExchangeRateMapper exchangeRateMapper;
    @Value("${api.nbp.url}")
    private String apiUrl;

    public void getCurrencyData() {
        try {
            String result = webClient
                    .get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            processCurrencyData(result);
        } catch (Exception ex) {
            log.error("Exception while finding data: [%s]".formatted(ex));
        }
    }

    public void processCurrencyData(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CurrencyData> currencyDataList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });

            for (CurrencyData currency : currencyDataList) {
                for (Rate rate : currency.rates()) {
                    Optional<ExchangeRateEntity> existingRate = Optional.ofNullable(exchangeRateJpaRepository.findByCode(rate.code()));

                    checkIfRateIsAlreadyPresent(rate, existingRate);
                }
            }
            checkIfPlnRateIsNotNull();
        } catch (Exception ex) {
            log.error("Error occurred during processing currency data: [%s]".formatted(ex));
        }
    }

    private void checkIfRateIsAlreadyPresent(Rate rate, Optional<ExchangeRateEntity> existingExchangeRate) {
        if (existingExchangeRate.isPresent()) {
            var existing = existingExchangeRate.get();
            existing.setCurrency(rate.currency());
            existing.setMid(rate.mid());
            exchangeRateJpaRepository.save(existing);
            log.info("Rate updated with code: [%s]".formatted(existing.getCode()));
        } else {
            var exchangeRate = ExchangeRateEntity
                    .builder()
                    .currency(rate.currency())
                    .code(rate.code())
                    .mid(rate.mid())
                    .build();
            exchangeRateJpaRepository.save(exchangeRate);
            log.info("New rate was saved with code: [%s] and mid: [%s]".formatted(exchangeRate.getCode(), exchangeRate.getMid()));
        }
    }

    public void checkIfPlnRateIsNotNull() {
        var plnRate = exchangeRateJpaRepository.findByCode("PLN");
        if (plnRate == null) {
            var newPlnExchangeRate = ExchangeRateEntity
                    .builder()
                    .currency("Polski Złoty")
                    .code("PLN")
                    .mid(BigDecimal.valueOf(1.0))
                    .build();
            exchangeRateJpaRepository.save(newPlnExchangeRate);
            log.info("New PLN rate was saved!");
        }
    }

    public List<ExchangeRateResponseDto> findAllCurrency() {
        getCurrencyData();
        log.info("Returning all currencies");
        return exchangeRateJpaRepository.findAll().stream()
                .map(exchangeRateMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
