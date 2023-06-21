package ru.job4j.cinema.service;

import java.nio.file.Paths;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

class SimpleFileServiceTest {

    private FileRepository fileRepository;
    private SimpleFileService simpleFileService;
    private File file;

    @BeforeEach
    public void initRepository() {
        fileRepository = mock(FileRepository.class);
        simpleFileService = new SimpleFileService(fileRepository);
        String testFilePath = Paths.get("src", "test", "resources", "test.txt").toFile().getAbsolutePath();
        file = new File("test.txt", testFilePath);
    }

    @Test
    void whenGetFileByIdThenGetFileDto() {
        String expectedText = "test";
        FileDto expected = new FileDto(file.getName(), expectedText.getBytes());
        when(fileRepository.findById(any(Integer.class))).thenReturn(Optional.of(file));

        FileDto actual = simpleFileService.getFileById(1).get();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        assertThat(new String(actual.getContent())).isEqualTo(expectedText);
    }

    @Test
    void whenGetFileByWrongIdThenGetOptionalEmpty() {
        when(fileRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Optional<FileDto> actual = simpleFileService.getFileById(2);
        assertThat(actual).isEmpty();
    }

    @Test
    void whenGetFileByIdAndFileNotExistsThenExceptionThrown() {
        file.setPath("wrong");
        when(fileRepository.findById(any(Integer.class))).thenReturn(Optional.of(file));
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> simpleFileService.getFileById(1));
    }

}