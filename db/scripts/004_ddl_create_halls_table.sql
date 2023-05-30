CREATE TABLE IF NOT EXISTS halls
(
    id          serial  PRIMARY KEY,
    name        varchar NOT NULL,
    row_count   int     NOT NULL,
    place_count int     NOT NULL,
    description varchar NOT NULL
);