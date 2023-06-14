package ru.job4j.cinema.service;

import java.util.Optional;
import ru.job4j.cinema.dto.FileDto;

public interface FileService {

    Optional<FileDto> getFileById(int id);

}