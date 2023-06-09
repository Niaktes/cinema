package ru.job4j.cinema.service;

import java.util.Collection;
import ru.job4j.cinema.dto.FilmSessionDto;

public interface FilmSessionService {

    FilmSessionDto findSessionById(int id);

    Collection<FilmSessionDto> getAllSessions();

}