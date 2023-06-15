package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    static void initRepository() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.databaseClient(dataSource);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }
    /*
    Test Database was filled in sql script by liquibase.
     */

    @Test
    void whenFindByIdFilmSessionThenGetFilmSession() {
        FilmSession expectedSession = new FilmSession(1, 1, 1,
                LocalDateTime.of(2001, 1, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 11, 11, 11), 100);
        assertThat(sql2oFilmSessionRepository.findById(1)).usingRecursiveComparison()
                .isEqualTo(expectedSession);
    }

    @Test
    void whenGetAllFilmSessionsThenGetFilmSessionsCollection() {
        FilmSession expectedSession1 = new FilmSession(1, 1, 1,
                LocalDateTime.of(2001, 1, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 11, 11, 11), 100);
        FilmSession expectedSession2 = new FilmSession(2, 2, 2,
                LocalDateTime.of(2002, 2, 2, 2, 2, 2), LocalDateTime.of(2002, 2, 2, 12, 12, 12), 200);
        assertThat(sql2oFilmSessionRepository.getAll())
                .isEqualTo(List.of(expectedSession1, expectedSession2));
    }

}