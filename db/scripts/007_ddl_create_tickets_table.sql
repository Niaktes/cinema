CREATE TABLE IF NOT EXISTS tickets
(
    id              serial                                  PRIMARY KEY,
    session_id      int     REFERENCES film_sessions(id)    NOT NULL,
    row_number      int                                     NOT NULL,
    place_number    int                                     NOT NULL,
    user_id         int                                     NOT NULL,
    unique (session_id, row_number, place_number)
);