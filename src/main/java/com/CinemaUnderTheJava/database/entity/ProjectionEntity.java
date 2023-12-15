package com.cinemaUnderTheJava.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projection")
public class ProjectionEntity extends AbstractEntity {

    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    private FilmEntity film;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projection_id")
    private List<SeatEntity> seats;
}
