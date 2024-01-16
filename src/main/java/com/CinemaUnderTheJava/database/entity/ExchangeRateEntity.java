package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exchangeRate")
public class ExchangeRateEntity extends AbstractEntity {

    private String currency;
    private String code;
    private BigDecimal mid;
}
