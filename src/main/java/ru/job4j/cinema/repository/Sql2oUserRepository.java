package ru.job4j.cinema.repository;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.User;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oUserRepository.class);

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(
                    "INSERT INTO users(email, name, password) VALUES (:email, :name, :password)",
                    true
            );
            query.addParameter("email", user.getEmail());
            query.addParameter("name", user.getFullName());
            query.addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Sql2oException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(
                    "SELECT * FROM users WHERE email = :email AND password = :password"
            );
            query.addParameter("email", email);
            query.addParameter("password", password);
            User user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean deleteByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(
                    "DELETE FROM users WHERE email = :email AND password = :password"
            );
            query.addParameter("email", email);
            query.addParameter("password", password);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

}