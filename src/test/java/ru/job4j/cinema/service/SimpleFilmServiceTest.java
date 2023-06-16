package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;

class SimpleFilmServiceTest {

    private FilmRepository filmRepository;
    private GenreService genreService;
    private SimpleFilmService simpleFilmService;

    @BeforeEach
    public void initRepository() {
        filmRepository = mock(FilmRepository.class);
        genreService = mock(GenreService.class);
        simpleFilmService = new SimpleFilmService(filmRepository, genreService);
    }

    @Test
    void whenFindFilmByIdThenGetFilmPreview() {
        Film film = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Genre genre = new Genre(1, "GenreName1");
        when(filmRepository.findById(any(Integer.class))).thenReturn(film);
        when(genreService.findById(any(Integer.class))).thenReturn(genre);
        FilmPreview expected = new FilmPreview(film, genre);

        FilmPreview actual =  simpleFilmService.getFilmById(film.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetAllFilmsThenGetFilmsCollection() {
        Film film1 = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Film film2 = new Film(2, "FilmName2", "description2", 2000, 2, 20, 20, 1);
        Genre genre1 = new Genre(1, "GenreName1");
        Genre genre2 = new Genre(2, "GenreName2");
        when(filmRepository.getAll()).thenReturn(List.of(film1, film2));
        when(genreService.findById(1)).thenReturn(genre1);
        when(genreService.findById(2)).thenReturn(genre2);
        FilmPreview expectedFilmPreview1 = new FilmPreview(film1, genre1);
        FilmPreview expectedFilmPreview2 = new FilmPreview(film2, genre2);

        Collection<FilmPreview> actual = simpleFilmService.getAllFilms();

        assertThat(actual).isEqualTo(List.of(expectedFilmPreview1, expectedFilmPreview2));
    }

}