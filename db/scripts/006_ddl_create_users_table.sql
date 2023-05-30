CREATE TABLE IF NOT EXISTS users
(
    id          serial  PRIMARY KEY,
    full_name   varchar NOT NULL,
    email       varchar NOT NULL UNIQUE,
    password    varchar NOT NULL
);