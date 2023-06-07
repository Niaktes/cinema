package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreRepository;

@Service
public class SimpleGenreService implements GenreService {

    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Genre findById(int id) {
        return genreRepository.findById(id);
    }

}