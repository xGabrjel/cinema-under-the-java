package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import com.cinemaUnderTheJava.database.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {

    SeatEntity findByProjectionAndRowNumberAndSeatInRow(
            @Param("projection") ProjectionEntity projection,
            @Param("rowNumber") int rowNumber,
            @Param("seatInRow") int seatInRow
    );
}
