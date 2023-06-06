package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Optional<FilmPreview> getFilmById(int id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        Genre genre = genreRepository.findById(filmOptional.get().getGenreId());
        FilmPreview filmPreview = new FilmPreview(filmOptional.get(), genre);
        return Optional.of(filmPreview);
    }

    @Override
    public Collection<FilmPreview> getAllFilms() {
        Collection<Film> films = filmRepository.getAll();
        return films.stream()
                .map(film -> new FilmPreview(film, genreRepository.findById(film.getGenreId())))
                .toList();
    }

}