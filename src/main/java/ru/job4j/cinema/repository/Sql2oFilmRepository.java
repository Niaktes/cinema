package ru.job4j.cinema.repository;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Film> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FRON films WHERE id = :id");
            query.addParameter("id", id);
            Film film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Collection<Film> getAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }

}