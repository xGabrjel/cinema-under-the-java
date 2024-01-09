CREATE TABLE IF NOT EXISTS app_user (

    id                  SERIAL,
    uuid                UUID,
    first_name          VARCHAR(255),
    last_name           VARCHAR(255),
    email               VARCHAR(255),
    password            VARCHAR(255),
    role                VARCHAR(50)     CHECK (role IN ('ADMIN', 'USER')),
    activation_status   VARCHAR(50)     CHECK (activation_status IN ('ACTIVE', 'INACTIVE')),
    activation_token    VARCHAR(255),

    PRIMARY KEY(id),
    UNIQUE(email)
);