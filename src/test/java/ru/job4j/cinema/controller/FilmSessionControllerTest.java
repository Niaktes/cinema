package ru.job4j.cinema.controller;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

class FilmSessionControllerTest {

    private FilmSessionService filmSessionService;
    private FilmService filmService;
    private HallService hallService;
    private TicketService ticketService;
    private FilmSessionController filmSessionController;

    @BeforeEach
    void initService() {
        filmSessionService = mock(FilmSessionService.class);
        filmService = mock(FilmService.class);
        hallService = mock(HallService.class);
        ticketService = mock(TicketService.class);
        filmSessionController = new FilmSessionController(filmSessionService, filmService, hallService,
                ticketService);
    }

    @Test
    void whenRequestSessionsListPageThenGetPageWithFilmsHallsAndFilmSessions() {
        FilmPreview filmPreview1 = new FilmPreview();
        FilmPreview filmPreview2 = new FilmPreview();
        Hall hall1 = new Hall(1, "hallName1", 10, 10, "description1");
        Hall hall2 = new Hall(2, "hallName2", 20, 20, "description2");
        FilmSessionDto filmSessionDto1 = new FilmSessionDto(1, 1, 1, "01:01", "11:11", 100);
        FilmSessionDto filmSessionDto2 = new FilmSessionDto(2, 2, 2, "02:02", "12:12", 200);
        when(filmService.getAllFilms()).thenReturn(List.of(filmPreview1, filmPreview2));
        when(hallService.getAllHalls()).thenReturn(List.of(hall1, hall2));
        when(filmSessionService.getAllSessions()).thenReturn(List.of(filmSessionDto1, filmSessionDto2));

        Model model = new ConcurrentModel();
        String view = filmSessionController.getSessionsList(model);
        var actualFilmsAttribute = model.getAttribute("films");
        var actualHallsAttribute = model.getAttribute("halls");
        var actualFilmSessionsAttribute = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualFilmsAttribute).isEqualTo(List.of(filmPreview1, filmPreview2));
        assertThat(actualHallsAttribute).isEqualTo(List.of(hall1, hall2));
        assertThat(actualFilmSessionsAttribute).isEqualTo(List.of(filmSessionDto1, filmSessionDto2));
    }

    @Test
    void whenRequestFilmSessionIdPageThenGetPageWithFilmSessionFilmHallAndSortedTickets() {
        FilmSessionDto filmSessionDto = new FilmSessionDto();
        FilmPreview filmPreview = new FilmPreview();
        TicketDto ticketDto1 = new TicketDto(1, "10:10", "hallName1", "filmName1", 1, 1, 1);
        TicketDto ticketDto2 = new TicketDto(1, "10:10", "hallName1", "filmName1", 1, 2, 1);
        TicketDto ticketDto3 = new TicketDto(1, "10:10", "hallName1", "filmName1", 2, 2, 1);
        Hall hall = new Hall(1, "hallName1", 10, 10, "description1");
        when(filmSessionService.findSessionById(any(Integer.class))).thenReturn(filmSessionDto);
        when(ticketService.findAllBySessionId(any(Integer.class)))
                .thenReturn(List.of(ticketDto2, ticketDto3, ticketDto1));
        when(filmService.getFilmById(any(Integer.class))).thenReturn(filmPreview);
        when(hallService.getHallById(any(Integer.class))).thenReturn(hall);

        Model model = new ConcurrentModel();
        String view = filmSessionController.getById(model, any(Integer.class));
        var actualFilmSessionAttribute = model.getAttribute("filmSession");
        var actualFilmAttribute = model.getAttribute("film");
        var actualHallAttribute = model.getAttribute("hall");
        var actualTicketsAttribute = model.getAttribute("tickets");

        assertThat(view).isEqualTo("sessions/one");
        assertThat(actualFilmSessionAttribute).usingRecursiveComparison().isEqualTo(filmSessionDto);
        assertThat(actualFilmAttribute).usingRecursiveComparison().isEqualTo(filmPreview);
        assertThat(actualHallAttribute).usingRecursiveComparison().isEqualTo(hall);
        assertThat(actualTicketsAttribute)
                .isEqualTo(List.of(ticketDto1, ticketDto2, ticketDto3));
    }

}