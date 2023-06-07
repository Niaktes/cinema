package ru.job4j.cinema.repository;

import java.util.Collection;
import ru.job4j.cinema.model.Hall;

public interface HallRepository {

    Hall findById(int id);

    Collection<Hall> findAll();

}