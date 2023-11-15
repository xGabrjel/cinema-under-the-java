package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.FilmCategory;
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
@Table(name = "film")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int filmDurationInMinutes;
    @Enumerated(EnumType.STRING)
    private FilmCategory category;
}
