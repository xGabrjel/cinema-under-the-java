CREATE TABLE IF NOT EXISTS exchange_rate (

    id          SERIAL,
    currency    VARCHAR(255),
    code        VARCHAR(255),
    mid         NUMERIC,

    PRIMARY KEY(id),
    UNIQUE(code)
);