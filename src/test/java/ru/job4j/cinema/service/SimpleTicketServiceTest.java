package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.*;
import ru.job4j.cinema.repository.TicketRepository;

class SimpleTicketServiceTest {

    private TicketRepository ticketRepository;
    private HallService hallService;
    private FilmSessionService filmSessionService;
    private FilmService filmService;
    private SimpleTicketService simpleTicketService;

    @BeforeEach
    public void initRepository() {
        ticketRepository = mock(TicketRepository.class);
        hallService = mock(HallService.class);
        filmSessionService = mock(FilmSessionService.class);
        filmService = mock(FilmService.class);
        simpleTicketService = new SimpleTicketService(ticketRepository, hallService, filmSessionService,
                filmService);
    }

    @Test
    void whenSaveTicketThenGetTicketDtoOptional() {
        TicketDto expected = new TicketDto(1, "01:01", "hallName1", "FilmName1", 1, 1, 1);
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Hall hall = new Hall(1, "hallName1", 10, 10, "description1");
        FilmSessionDto filmSessionDto = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        Film film = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Genre genre = new Genre(1, "GenreName1");
        when(ticketRepository.save(any(Ticket.class))).thenReturn(Optional.of(ticket));
        when(hallService.getHallById(any(Integer.class))).thenReturn(hall);
        when(filmSessionService.findSessionById(any(Integer.class))).thenReturn(filmSessionDto);
        when(filmService.getFilmById(any(Integer.class))).thenReturn(new FilmPreview(film, genre));

        Optional<TicketDto> actual = simpleTicketService.save(ticket);

        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenSaveWrongTicketThenGetOptionalEmpty() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(Optional.empty());
        Optional<TicketDto> actual = simpleTicketService.save(new Ticket(1, 1, 1, 1, 1));
        assertThat(actual).isEmpty();
    }

    @Test
    void whenFindByIdTicketThenGetTicketDtoOptional() {
        TicketDto expected = new TicketDto(1, "01:01", "hallName1", "FilmName1", 1, 1, 1);
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Hall hall = new Hall(1, "hallName1", 10, 10, "description1");
        FilmSessionDto filmSessionDto = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        Film film = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Genre genre = new Genre(1, "GenreName1");
        when(ticketRepository.findById(any(Integer.class))).thenReturn(Optional.of(ticket));
        when(hallService.getHallById(any(Integer.class))).thenReturn(hall);
        when(filmSessionService.findSessionById(any(Integer.class))).thenReturn(filmSessionDto);
        when(filmService.getFilmById(any(Integer.class))).thenReturn(new FilmPreview(film, genre));

        Optional<TicketDto> actual = simpleTicketService.findById(1);

        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenFindByWrongIdThenGetOptionalEmpty() {
        when(ticketRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Optional<TicketDto> actual = simpleTicketService.findById(1);
        assertThat(actual).isEmpty();
    }

    @Test
    void whenFindAllBySessionIdThenGetTicketDtoCollection() {
        TicketDto expected1 = new TicketDto(1, "01:01", "hallName1", "FilmName1", 1, 1, 1);
        TicketDto expected2 = new TicketDto(2, "01:01", "hallName2", "FilmName2", 2, 2, 2);
        Ticket ticket1 = new Ticket(1, 1, 1, 1, 1);
        Ticket ticket2 = new Ticket(2, 1, 2, 2, 2);
        Hall hall1 = new Hall(1, "hallName1", 10, 10, "description1");
        FilmSessionDto filmSessionDto1 = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        Film film1 = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Genre genre1 = new Genre(1, "GenreName1");
        when(ticketRepository.findAllBySessionId(1)).thenReturn(List.of(ticket1, ticket2));
        when(hallService.getHallById(1)).thenReturn(hall1);
        when(filmSessionService.findSessionById(1)).thenReturn(filmSessionDto1);
        when(filmService.getFilmById(1)).thenReturn(new FilmPreview(film1, genre1));

        Collection<TicketDto> actual = simpleTicketService.findAllBySessionId(1);

        assertThat(actual).isEqualTo(List.of(expected1, expected2));
    }

}