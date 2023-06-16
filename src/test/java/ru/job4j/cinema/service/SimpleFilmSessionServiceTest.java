package ru.job4j.cinema.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionRepository;

class SimpleFilmSessionServiceTest {

    private FilmSessionRepository filmSessionRepository;
    private SimpleFilmSessionService simpleFilmSessionService;

    @BeforeEach
    public void initRepository() {
        filmSessionRepository = mock(FilmSessionRepository.class);
        simpleFilmSessionService = new SimpleFilmSessionService(filmSessionRepository);
    }

    @Test
    void whenFindSessionByIdThenGetFilmSessionDto() {
        FilmSession filmSession = new FilmSession(1, 1, 1,
                LocalDateTime.of(2001, 1, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 11, 11, 11), 100);
        FilmSessionDto expected = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        when(filmSessionRepository.findById(any(Integer.class))).thenReturn(filmSession);

        FilmSessionDto actual = simpleFilmSessionService.findSessionById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenGetAllSessionsThenGetFilmSessionsCollection() {
        FilmSession filmSession1 = new FilmSession(1, 1, 1,
                LocalDateTime.of(2001, 1, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 11, 11, 11), 100);
        FilmSession filmSession2 = new FilmSession(2, 2, 2,
                LocalDateTime.of(2002, 2, 2, 2, 2, 2), LocalDateTime.of(2002, 2, 2, 12, 12, 12), 200);
        FilmSessionDto expectedFilmDto1 = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        FilmSessionDto expectedFilmDto2 = new FilmSessionDto(2, 2, 2, "02:02", "12:12", 200);
        when(filmSessionRepository.getAll()).thenReturn(List.of(filmSession1, filmSession2));

        Collection<FilmSessionDto> actual = simpleFilmSessionService.getAllSessions();

        assertThat(actual).isEqualTo(List.of(expectedFilmDto1, expectedFilmDto2));
    }

}