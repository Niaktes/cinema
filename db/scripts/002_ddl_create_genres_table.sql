CREATE TABLE IF NOT EXISTS genres
(
    id      serial      PRIMARY KEY,
    name    varchar     NOT NULL UNIQUE
);
