package ru.job4j.cinema.repository;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.cinema.model.Film;

public interface FilmRepository {

    Optional<Film> findById(int id);

    Collection<Film> getAll();

}