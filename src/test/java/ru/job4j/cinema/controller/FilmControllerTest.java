package ru.job4j.cinema.controller;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.service.FilmService;

class FilmControllerTest {

    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    void initService() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    void whenRequestFilmsListPageThenGetPageWithFilms() {
        FilmPreview filmPreview1 = new FilmPreview();
        FilmPreview filmPreview2 = new FilmPreview();
        when(filmService.getAllFilms()).thenReturn(List.of(filmPreview1, filmPreview2));

        Model model = new ConcurrentModel();
        String view = filmController.getAllFilms(model);
        var actualAttribute = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualAttribute).isEqualTo(List.of(filmPreview1, filmPreview2));
    }

}