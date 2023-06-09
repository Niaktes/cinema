package ru.job4j.cinema.controller;

import java.util.Collection;
import java.util.Comparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {

    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final HallService hallService;
    private final TicketService ticketService;

    public FilmSessionController(FilmSessionService filmSessionService, FilmService filmService,
                                 HallService hallService, TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getSessionsList(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("filmSessions", filmSessionService.getAllSessions());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        FilmSessionDto filmSession = filmSessionService.findSessionById(id);
        Collection<TicketDto> ticketsSorted = ticketService.findAllBySessionId(id)
                .stream()
                .sorted(Comparator.comparing(TicketDto::getRowNumber).thenComparing(TicketDto::getPlaceNumber))
                .toList();
        model.addAttribute("filmSession", filmSession);
        model.addAttribute("film", filmService.getFilmById(filmSession.getFilmId()));
        model.addAttribute("hall", hallService.getHallById(filmSession.getHallId()));
        model.addAttribute("tickets", ticketsSorted);
        return "sessions/one";
    }

}