package ru.job4j.cinema.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreService genreService;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreService genreService) {
        this.filmRepository = sql2oFilmRepository;
        this.genreService = genreService;
    }

    @Override
    public FilmPreview getFilmById(int id) {
        Film film = filmRepository.findById(id);
        return new FilmPreview(film, genreService.findById(film.getGenreId()));
    }

    @Override
    public Collection<FilmPreview> getAllFilms() {
        Collection<Film> films = filmRepository.getAll();
        return films.stream()
                .map(film -> new FilmPreview(film, genreService.findById(film.getGenreId())))
                .toList();
    }

}