package ru.job4j.cinema.repository;

import java.util.Optional;
import ru.job4j.cinema.model.File;

public interface FileRepository {

    Optional<File> findById(int id);

}