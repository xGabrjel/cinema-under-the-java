package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.FilmCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private FilmCategory category;
    private int filmDurationInMinutes;
}
