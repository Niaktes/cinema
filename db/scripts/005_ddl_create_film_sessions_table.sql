CREATE TABLE IF NOT EXISTS film_sessions
(
    id          serial                          PRIMARY KEY,
    film_id     int     REFERENCES films(id)    NOT NULL,
    hall_id     int     REFERENCES halls(id)    NOT NULL,
    start_time  timestamp                       NOT NULL,
    end_time    timestamp                       NOT NULL,
    price       int                             NOT NULL
);