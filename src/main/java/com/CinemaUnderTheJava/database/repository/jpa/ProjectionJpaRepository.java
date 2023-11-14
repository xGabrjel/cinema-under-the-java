package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProjectionJpaRepository extends JpaRepository<ProjectionEntity, Long> {

    List<ProjectionEntity> findByDate(LocalDate date);
}
