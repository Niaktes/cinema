package ru.job4j.cinema.controller;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void initService() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        httpServletRequest = new MockHttpServletRequest();
    }

    @Test
    void whenRequestRegistrationPageThenGetPage() {
        String view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    void whenPostRegistrationDataThenRedirectToLoginPage() {
        User user = new User(1, "fullName", "email", "password");
        when(userService.save(any(User.class))).thenReturn(Optional.of(user));

        Model model = new ConcurrentModel();
        String view = userController.register(user, model);

        assertThat(view).isEqualTo("redirect:/users/login");
    }

    @Test
    void whenPostRegistrationDataWithAlreadyExistEmailThenGetRegisterPageWithMessage() {
        String expectedErrorMessage = "Пользователь с такой почтой уже существует";
        when(userService.save(any(User.class))).thenReturn(Optional.empty());

        Model model = new ConcurrentModel();
        String view = userController.register(any(User.class), model);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/register");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void whenRequestLoginPageThenGetPage() {
        String view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    void whenPostLoginDataThenGetSessionAttributesAndRedirectToStartPage() {
        User user = new User(1, "fullName", "email", "password");
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));

        Model model = new ConcurrentModel();
        String view = userController.loginUser(user, model, httpServletRequest);
        var actualAttribute = httpServletRequest.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/");
        assertThat(actualAttribute).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenPostWrongLoginDataThenGetLoginPageWithMessage() {
        String expectedErrorMessage = "Почта или пароль введены неверно!";
        when(userService.save(any(User.class))).thenReturn(Optional.empty());

        Model model = new ConcurrentModel();
        String view = userController.loginUser(new User(), model, httpServletRequest);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void whenRequestLogoutThenHttpSessionInvalidateAndRedirectToLoginPage() {
        User user = new User(1, "fullName", "email", "password");
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", user);

        String view = userController.logout(session);
        var actualUser = httpServletRequest.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/users/login");
        assertThat(actualUser).isNull();
    }

}