package ru.job4j.cinema.controller;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        Optional<TicketDto> ticketOptional = ticketService.save(ticket);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("error", "Кажется, указанное вами место уже занято. Пожалуйста, выберите "
                    + "другое место и повторите покупку билета");
            return "tickets/buyFail";
        }
        model.addAttribute("ticket", ticketOptional.get());
        return "tickets/buySuccess";
    }

}
