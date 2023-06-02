package ru.job4j.cinema.repository;

import java.util.Optional;
import ru.job4j.cinema.model.Genre;

public interface GenreRepository {

    Optional<Genre> findById(int id);

}