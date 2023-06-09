package ru.job4j.cinema.repository;

import java.util.Collection;
import ru.job4j.cinema.model.Film;

public interface FilmRepository {

    Film findById(int id);

    Collection<Film> getAll();

}