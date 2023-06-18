package ru.job4j.cinema.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexControllerTest {

    private IndexController indexController;

    @BeforeEach
    public void initService() {
        indexController = new IndexController();
    }

    @Test
    void whenRequestIndexPageThenGetIndexPage() {
        String view = indexController.getIndex();
        assertThat(view).isEqualTo("index");
    }

}