package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    static void initRepository() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oHallRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.databaseClient(dataSource);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }
    /*
    Test Database was filled in sql script by liquibase.
     */

    @Test
    void whenFindByIdFilmThenGetFilm() {
        Film expectedFilm = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        assertThat(sql2oFilmRepository.findById(1)).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    @Test
    void whenGetAllFilmsThenGetFilmsCollection() {
        Film expectedFilm1 = new Film(1, "FilmName1", "description1", 2000, 1, 10, 10, 1);
        Film expectedFilm2 = new Film(2, "FilmName2", "description2", 2000, 2, 20, 20, 1);
        assertThat(sql2oFilmRepository.getAll()).isEqualTo(List.of(expectedFilm1, expectedFilm2));
    }

}