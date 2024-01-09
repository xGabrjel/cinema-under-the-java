CREATE TABLE IF NOT EXISTS ticket (

    id                  SERIAL,
    uuid                UUID,
    name                VARCHAR(255),
    film_title          VARCHAR(255),
    projection_date     DATE,
    projection_time     TIME,
    ticket_price        NUMERIC(19, 2),
    row_number          INT,
    seat_in_row         INT,
    room_number         INT,
    user_id             BIGINT,
    status              VARCHAR(50)     CHECK (status IN ('ACTIVE', 'CANCELLED')),
    ticket_type         VARCHAR(50)     CHECK (ticket_type IN ('NORMAL', 'DISCOUNTED')),
    ticket_currency     VARCHAR(5)      CHECK (ticket_currency IN ('THB', 'USD', 'AUD', 'HKD', 'CAD', 'NZD', 'SGD', 'EUR', 'HUF', 'CHF', 'GBP', 'UAH', 'JPY', 'CZK', 'DKK', 'ISK', 'NOK', 'SEK', 'RON', 'BGN', 'TRY', 'ILS', 'CLP', 'PHP', 'MXN', 'ZAR', 'BRL', 'MYR', 'IDR', 'INR', 'KRW', 'CNY', 'XDR', 'PLN')),

    PRIMARY KEY(id)
);