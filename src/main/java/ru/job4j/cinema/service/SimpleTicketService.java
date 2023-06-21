package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final HallService hallService;
    private final FilmSessionService filmSessionService;
    private final FilmService filmService;

    public SimpleTicketService(TicketRepository sql2oTicketRepository, HallService hallService,
                               FilmSessionService filmSessionService, FilmService filmService) {
        this.ticketRepository = sql2oTicketRepository;
        this.hallService = hallService;
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
    }

    @Override
    public Optional<TicketDto> save(Ticket ticket) {
        Optional<Ticket> ticketOptional = ticketRepository.save(ticket);
        return ticketOptional.map(this::createFromTicket);
    }

    @Override
    public Optional<TicketDto> findById(int id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.map(this::createFromTicket);

    }

    @Override
    public Collection<TicketDto> findAllBySessionId(int id) {
        Collection<Ticket> tickets = ticketRepository.findAllBySessionId(id);
        return tickets.stream().map(this::createFromTicket).toList();
    }

    private TicketDto createFromTicket(Ticket ticket) {
        FilmSessionDto session = filmSessionService.findSessionById(ticket.getSessionId());
        return new TicketDto(
                ticket.getId(),
                session.getStartTime(),
                hallService.getHallById(session.getHallId()).getName(),
                filmService.getFilmById(session.getFilmId()).getName(),
                ticket.getRowNumber(),
                ticket.getPlaceNumber(),
                ticket.getUserId()
        );
    }

}