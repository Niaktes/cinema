package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.cinema.dto.FilmSessionDto;

public interface FilmSessionService {

    Optional<FilmSessionDto> findSessionById(int id);

    Collection<FilmSessionDto> getAllSessions();

}