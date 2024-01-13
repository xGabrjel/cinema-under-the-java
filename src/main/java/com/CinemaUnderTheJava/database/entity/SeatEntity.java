package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class SeatEntity extends AbstractEntity {

    private int rowNumber;
    private int seatInRow;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @ManyToOne
    @JoinColumn(name = "projection_id")
    @JsonIgnore
    private ProjectionEntity projection;
}
