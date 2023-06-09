package ru.job4j.cinema.service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionRepository;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
    }

    @Override
    public FilmSessionDto findSessionById(int id) {
        return createFromFilmSession(filmSessionRepository.findById(id));
    }

    @Override
    public Collection<FilmSessionDto> getAllSessions() {
        Collection<FilmSession> sessions = filmSessionRepository.getAll();
        return sessions.stream()
                .map(this::createFromFilmSession)
                .toList();
    }

    private FilmSessionDto createFromFilmSession(FilmSession filmSession) {
        return new FilmSessionDto(
                filmSession.getId(),
                filmSession.getFilmId(),
                filmSession.getHallId(),
                filmSession.getStartTime().format(FORMATTER),
                filmSession.getEndTime().format(FORMATTER),
                filmSession.getPrice()
        );
    }

}