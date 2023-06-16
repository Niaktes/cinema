package ru.job4j.cinema.service;

import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

class SimpleHallServiceTest {

    private HallRepository hallRepository;
    private SimpleHallService simpleHallService;

    @BeforeEach
    public void initRepository() {
        hallRepository = mock(HallRepository.class);
        simpleHallService = new SimpleHallService(hallRepository);
    }

    @Test
    void whenGetHallByIdThenGetHall() {
        Hall hall = new Hall(1, "hallName1", 10, 10, "description1");
        when(hallRepository.findById(any(Integer.class))).thenReturn(hall);

        Hall foundHall = simpleHallService.getHallById(1);

        assertThat(foundHall).usingRecursiveComparison().isEqualTo(hall);
    }

    @Test
    void whenGetAllHallsThenGetHallsCollection() {
        Hall hall1 = new Hall(1, "hallName1", 10, 10, "description1");
        Hall hall2 = new Hall(2, "hallName2", 20, 20, "description2");
        when(hallRepository.findAll()).thenReturn(List.of(hall1, hall2));

        Collection<Hall> actual = simpleHallService.getAllHalls();

        assertThat(actual).isEqualTo(List.of(hall1, hall2));
    }

}