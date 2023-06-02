package ru.job4j.cinema.repository;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.cinema.model.FilmSession;

public interface FilmSessionRepository {

    Optional<FilmSession> findById(int id);

    Collection<FilmSession> getAll();

}