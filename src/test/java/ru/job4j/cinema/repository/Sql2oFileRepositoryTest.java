package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

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
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }
    /*
    Test Database was filled in sql script by liquibase.
     */

    @Test
    void whenFindFileByIdThenGetFileOptional() {
        File expectedFile = new File("test.txt", "src\\test\\resources\\test.txt");
        expectedFile.setId(1);
        assertThat(sql2oFileRepository.findById(1).get()).usingRecursiveComparison().isEqualTo(expectedFile);
    }

    @Test
    void whenFindFileByWrongIdThenGetOptionalEmpty() {
        assertThat(sql2oFileRepository.findById(2)).isEmpty();
    }

}