CREATE TABLE IF NOT EXISTS seats (

    id              SERIAL,
    row_number      INT,
    seat_in_row     INT,
    status          VARCHAR(50) CHECK (status IN ('TAKEN', 'AVAILABLE')),
    projection_id   BIGINT,

    PRIMARY KEY(id),
    FOREIGN KEY (projection_id) REFERENCES projection(id) ON DELETE CASCADE
);