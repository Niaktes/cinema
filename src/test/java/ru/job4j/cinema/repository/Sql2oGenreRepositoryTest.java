package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Genre;

class Sql2oGenreRepositoryTest {

    private static Sql2oGenreRepository sql2oGenreRepository;

    @BeforeAll
    static void initRepository() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.databaseClient(dataSource);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }
    /*
    Test Database was filled in sql script by liquibase.
     */

    @Test
    void whenFindGenreByIdThenGetGenre() {
        Genre expectedGenre = new Genre(2, "GenreName2");
        assertThat(sql2oGenreRepository.findById(2)).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

}