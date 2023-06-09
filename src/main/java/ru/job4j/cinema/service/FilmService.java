package ru.job4j.cinema.service;

import java.util.Collection;
import ru.job4j.cinema.dto.FilmPreview;

public interface FilmService {

    FilmPreview getFilmById(int id);

    Collection<FilmPreview> getAllFilms();

}