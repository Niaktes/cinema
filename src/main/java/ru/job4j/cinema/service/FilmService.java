package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.cinema.dto.FilmPreview;

public interface FilmService {

    Optional<FilmPreview> getFilmById(int id);

    Collection<FilmPreview> getAllFilms();

}