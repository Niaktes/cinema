package ru.job4j.cinema.repository;

import java.util.Optional;
import ru.job4j.cinema.model.User;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean deleteByEmailAndPassword(String email, String password);

}