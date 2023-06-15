package ru.job4j.cinema.repository;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2o sql2o;

    @BeforeAll
    static void initRepository() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oTicketRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");
        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(dataSource);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    void clearDataBase() throws Sql2oException {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM tickets").executeUpdate();
        }
    }

    @Test
    void whenSaveAndFindByIdThenGetTicket() {
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Ticket savedTicket = sql2oTicketRepository.save(ticket).get();
        Ticket foundTicket = sql2oTicketRepository.findById(ticket.getId()).get();
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
        assertThat(foundTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    void whenSaveTicketToOccupiedPlaceThenGetOptionalEmpty() {
        sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1));
        Ticket otherTicket = new Ticket(2, 1, 1, 1, 2);
        Optional<Ticket> savedOtherTicket = sql2oTicketRepository.save(otherTicket);
        assertThat(savedOtherTicket).isEmpty();
    }

    @Test
    void whenTryToFindNonexistentTicketThenGetOptionalEmpty() {
        sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1));
        Optional<Ticket> nonexistentTicket = sql2oTicketRepository.findById(2);
        assertThat(nonexistentTicket).isEmpty();
    }

    @Test
    void whenFindTicketsBySessionIdThenGetCollectionOfTickets() {
        Ticket ticket1 = new Ticket(1, 1, 1, 1, 1);
        Ticket ticket2 = new Ticket(2, 1, 1, 2, 1);
        Ticket ticket3 = new Ticket(3, 1, 5, 7, 2);
        Ticket ticket4 = new Ticket(1, 2, 1, 1, 1);
        sql2oTicketRepository.save(ticket1);
        sql2oTicketRepository.save(ticket2);
        sql2oTicketRepository.save(ticket3);
        sql2oTicketRepository.save(ticket4);
        assertThat(sql2oTicketRepository.findAllBySessionId(1))
                .isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

}