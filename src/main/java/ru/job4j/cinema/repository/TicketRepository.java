package ru.job4j.cinema.repository;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.cinema.model.Ticket;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findAllBySessionId(int id);

}