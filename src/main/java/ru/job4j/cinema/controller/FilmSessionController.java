package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {

    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final HallService hallService;

    public FilmSessionController(FilmSessionService filmSessionService, FilmService filmService,
                                 HallService hallService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getSessionsList(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("filmSessions", filmSessionService.getAllSessions());
        return "sessions/list";
    }

}