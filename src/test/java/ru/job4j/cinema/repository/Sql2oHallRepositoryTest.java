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
import ru.job4j.cinema.model.Hall;

class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

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
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }
    /*
    Test Database was filled in sql script by liquibase.
     */

    @Test
    void whenFindHallByIdThenGetHall() {
        Hall expectedHall = new Hall(1, "hallName1", 10, 10, "description1");
        Hall actualHall = sql2oHallRepository.findById(1);
        assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }

    @Test
    void whenFindAllHallsThenGetCollectionOfHalls() {
        Hall expectedHall1 = new Hall(1, "hallName1", 10, 10, "description1");
        Hall expectedHall2 = new Hall(2, "hallName2", 20, 20, "description2");
        assertThat(sql2oHallRepository.findAll()).isEqualTo(List.of(expectedHall1, expectedHall2));
    }

}