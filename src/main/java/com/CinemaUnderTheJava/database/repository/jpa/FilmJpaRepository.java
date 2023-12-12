package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.FilmEntity;
import com.cinemaUnderTheJava.database.enums.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FilmJpaRepository extends JpaRepository<FilmEntity, Long> {

    Optional<FilmEntity> findByTitle(String title);

    List<FilmEntity> findByCategory(FilmCategory category);
}
