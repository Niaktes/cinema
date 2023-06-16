package ru.job4j.cinema.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreRepository;

class SimpleGenreServiceTest {

    private GenreRepository genreRepository;
    private SimpleGenreService simpleGenreService;

    @BeforeEach
    public void initRepository() {
        genreRepository = mock(GenreRepository.class);
        simpleGenreService = new SimpleGenreService(genreRepository);
    }

    @Test
    void whenFindGenreByIdThenGetGenre() {
        Genre genre = new Genre(1, "GenreName1");
        when(genreRepository.findById(any(Integer.class))).thenReturn(genre);

        Genre foundGenre = genreRepository.findById(1);

        assertThat(foundGenre).usingRecursiveComparison().isEqualTo(genre);
    }

}