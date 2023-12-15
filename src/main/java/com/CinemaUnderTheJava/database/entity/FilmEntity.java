package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.FilmCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
public class FilmEntity extends AbstractEntity {

    private String title;
    private int filmDurationInMinutes;
    @Enumerated(EnumType.STRING)
    private FilmCategory category;
}
