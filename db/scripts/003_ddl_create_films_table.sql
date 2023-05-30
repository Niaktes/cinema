CREATE TABLE IF NOT EXISTS films
(
    id                  serial                          PRIMARY KEY,
    name                varchar                         NOT NULL,
    description         varchar                         NOT NULL,
    "year"              int                             NOT NULL,
    genre_id            int     REFERENCES genres(id)   NOT NULL,
    minimal_age         int                             NOT NULL,
    duration_in_minutes int                             NOT NULL,
    file_id             int     REFERENCES files(id)    NOT NULL
);