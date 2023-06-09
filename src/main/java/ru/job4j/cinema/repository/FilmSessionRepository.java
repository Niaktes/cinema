package ru.job4j.cinema.repository;

import java.util.Collection;
import ru.job4j.cinema.model.FilmSession;

public interface FilmSessionRepository {

    FilmSession findById(int id);

    Collection<FilmSession> getAll();

}