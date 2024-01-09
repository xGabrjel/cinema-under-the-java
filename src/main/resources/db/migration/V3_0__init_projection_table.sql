CREATE TABLE IF NOT EXISTS projection (

    id          SERIAL,
    date        DATE,
    time        TIME,
    film_id     BIGINT,

    PRIMARY KEY(id),
    FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE
);