package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {
}
