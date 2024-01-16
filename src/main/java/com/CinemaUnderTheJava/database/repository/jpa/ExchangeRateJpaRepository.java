package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateJpaRepository extends JpaRepository<ExchangeRateEntity, Long> {

    ExchangeRateEntity findByCode(String code);
}
