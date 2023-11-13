package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmJpaRepository extends JpaRepository<FilmEntity, Long> {
}
