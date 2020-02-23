package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketFiles;

import java.util.List;

public interface TicketFileRepository extends JpaRepository<TicketFiles, Integer> {

    TicketFiles findById(int id);

    List<TicketFiles> findAllByOrderById();
    List<TicketFiles> findByTicketOrderById(Ticket ticket);

}
