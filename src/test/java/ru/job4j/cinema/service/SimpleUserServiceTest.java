package ru.job4j.cinema.service;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

class SimpleUserServiceTest {

    private UserRepository userRepository;
    private SimpleUserService simpleUserService;

    @BeforeEach
    public void initRepository() {
        userRepository = mock(UserRepository.class);
        simpleUserService = new SimpleUserService(userRepository);
    }

    @Test
    void whenSaveUserThenGetUserOptional() {
        User user = new User(1, "name", "email", "password");
        when(userRepository.save(any(User.class))).thenReturn(Optional.of(user));
        Optional<User> savedUser = simpleUserService.save(user);
        assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenSaveWrongUserThenGetOptionalEmpty() {
        when(userRepository.save(any(User.class))).thenReturn(Optional.empty());
        Optional<User> savedUser = simpleUserService.save(new User(1, "name", "email", "password"));
        assertThat(savedUser).isEmpty();
    }

    @Test
    void whenFindByEmailAndPasswordThenGetUserOptional() {
        User user = new User(1, "name", "email", "password");
        when(userRepository.findByEmailAndPassword(any(String.class), any(String.class)))
                .thenReturn(Optional.of(user));
        Optional<User> foundUser = simpleUserService.findByEmailAndPassword("name", "password");
        assertThat(foundUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenFindByWrongEmailAndPasswordThenGetOptionalEmpty() {
        when(userRepository.findByEmailAndPassword(any(String.class), any(String.class)))
                .thenReturn(Optional.empty());
        Optional<User> foundUser = simpleUserService.findByEmailAndPassword("name", "password");
        assertThat(foundUser).isEmpty();
    }

}