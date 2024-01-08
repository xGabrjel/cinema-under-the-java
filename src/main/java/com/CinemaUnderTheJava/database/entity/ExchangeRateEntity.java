package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "exchangeRate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRateEntity extends AbstractEntity {
    private String currency;
    private String code;
    private BigDecimal mid;
}
