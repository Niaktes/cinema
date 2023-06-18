package ru.job4j.cinema.controller;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

class TicketControllerTest {

    private TicketService ticketService;
    private TicketController ticketController;

    @BeforeEach
    void initService() {
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService);
    }

    @Test
    void whenPostCreateTicketThenGetBuySuccessPageWithTicketDto() {
        TicketDto ticketDto = new TicketDto(1, "01:01", "hallName1", "filmName1", 1, 1, 1);
        when(ticketService.save(any(Ticket.class))).thenReturn(Optional.of(ticketDto));

        Model model = new ConcurrentModel();
        String view = ticketController.create(new Ticket(), model);
        var actualAttribute = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/buySuccess");
        assertThat(actualAttribute).usingRecursiveComparison().isEqualTo(ticketDto);
    }

    @Test
    void whenPostCreateTicketOnOccupiedSeatThenGetBuyFailPageWithErrorMessage() {
        String expectedErrorMessage = "Кажется, указанное вами место уже занято. Пожалуйста, выберите "
                + "другое место и повторите покупку билета";
        when(ticketService.save(any(Ticket.class))).thenReturn(Optional.empty());

        Model model = new ConcurrentModel();
        String view = ticketController.create(new Ticket(), model);
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("tickets/buyFail");
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

}