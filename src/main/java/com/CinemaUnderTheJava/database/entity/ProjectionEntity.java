package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projection")
public class ProjectionEntity extends AbstractEntity {

    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    private FilmEntity film;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "projection_id")
    private List<SeatEntity> seats;
}
