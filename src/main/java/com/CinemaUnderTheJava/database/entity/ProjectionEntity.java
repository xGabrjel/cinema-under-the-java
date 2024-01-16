package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@With
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projection")
public class ProjectionEntity extends AbstractEntity {

    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    private FilmEntity film;
    @JoinColumn(name = "projection_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SeatEntity> seats;
}
