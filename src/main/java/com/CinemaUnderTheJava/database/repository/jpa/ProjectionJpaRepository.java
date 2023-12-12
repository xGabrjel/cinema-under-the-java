package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ProjectionJpaRepository extends JpaRepository<ProjectionEntity, Long> {

    List<ProjectionEntity> findByDate(LocalDate date);
}
