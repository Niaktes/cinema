INSERT INTO files(name, path) VALUES
('test.txt', 'src\test\resources\test.txt');

INSERT INTO genres(name) VALUES
('GenreName1'),
('GenreName2');

INSERT INTO films(name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES
('FilmName1', 'description1', 2000, 1, 10, 10, 1),
('FilmName2', 'description2', 2000, 2, 20, 20, 1);

INSERT INTO halls(name, row_count, place_count, description) VALUES
('hallName1', 10, 10, 'description1'),
('hallName2', 20, 20, 'description2');

INSERT INTO film_sessions(film_id, hall_id, start_time, end_time, price) VALUES
(1, 1, '2001-01-01 01:01:01-03', '2001-01-01 11:11:11-03', 100),
(2, 2, '2002-02-02 02:02:02-03', '2002-02-02 12:12:12-03', 200);