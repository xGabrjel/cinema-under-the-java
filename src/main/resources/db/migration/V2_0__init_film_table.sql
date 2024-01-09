CREATE TABLE IF NOT EXISTS film (

    id                          SERIAL,
    title                       VARCHAR(255),
    film_duration_in_minutes    INT,
    category                    VARCHAR(50) CHECK (category IN ('DRAMA', 'ACTION', 'HORROR', 'COMEDY', 'FANTASY', 'MYSTERY', 'ROMANCE', 'WESTERN', 'THRILLER')),

    PRIMARY KEY(id)
);