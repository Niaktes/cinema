package ru.job4j.cinema.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Hall getHallById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public Collection<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

}