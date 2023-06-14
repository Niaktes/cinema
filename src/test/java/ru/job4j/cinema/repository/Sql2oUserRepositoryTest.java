package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;
    private static User user;

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
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        user = new User(0, "fullName", "email", "password");
    }

    @AfterEach
    public void deleteUser() {
        sql2oUserRepository.deleteByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Test
    void whenSaveAndFindThenGetUser() {
        User savedUser = sql2oUserRepository.save(user).get();
        User foundUser = sql2oUserRepository
                .findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
        assertThat(foundUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenSaveAlreadyExistUserThenGetOptionalEmpty() {
        sql2oUserRepository.save(user);
        User otherUser = new User(1, "otherName", user.getEmail(), "otherPassword");
        Optional<User> otherSavedUser = sql2oUserRepository.save(otherUser);
        assertThat(otherSavedUser).isEmpty();
    }

    @Test
    void whenTryToFindUserByWrongEmailOrPasswordThenGetOptionalEmpty() {
        sql2oUserRepository.save(user);
        Optional<User> wrongEmailUser = sql2oUserRepository.findByEmailAndPassword(
                "wrongEmail", "password"
        );
        Optional<User> wrongPasswordUser = sql2oUserRepository.findByEmailAndPassword(
                "email", "wrongPassword"
        );
        assertThat(wrongEmailUser).isEmpty();
        assertThat(wrongPasswordUser).isEmpty();
    }

    @Test
    void whenUserDeletedThenGetTrue() {
        sql2oUserRepository.save(user);
        boolean deleted = sql2oUserRepository.deleteByEmailAndPassword(user.getEmail(), user.getPassword());
        boolean deletedAgain = sql2oUserRepository.deleteByEmailAndPassword(
                user.getEmail(), user.getPassword());
        assertThat(deleted).isTrue();
        assertThat(deletedAgain).isFalse();
    }

}