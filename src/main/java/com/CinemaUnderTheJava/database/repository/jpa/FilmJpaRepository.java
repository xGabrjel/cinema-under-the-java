package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmJpaRepository extends JpaRepository<FilmEntity, Long> {

    Optional<FilmEntity> findByTitle(String title);

    List<FilmEntity> findByCategory(FilmCategory category);
}
