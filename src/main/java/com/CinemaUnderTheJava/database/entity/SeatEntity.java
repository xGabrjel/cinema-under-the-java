package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rowNumber;
    private int seatInRow;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @ManyToOne
    @JoinColumn(name = "projection_id")
    private ProjectionEntity projection;
}
