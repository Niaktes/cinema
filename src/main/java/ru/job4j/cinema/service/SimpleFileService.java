package ru.job4j.cinema.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<FileDto> getAllFiles() {
        Collection<File> files = fileRepository.getAll();
        return files.stream()
                .map(file -> new FileDto(file.getName(), readFileAsBytes(file.getPath()))).toList();
    }

}