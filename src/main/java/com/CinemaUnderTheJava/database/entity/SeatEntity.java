package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@With
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class SeatEntity extends AbstractEntity {

    private int rowNumber;
    private int seatInRow;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "projection_id")
    private ProjectionEntity projection;
}
