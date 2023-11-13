package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SeatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rowNumber;
    private int number;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
