package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketHistory;

import java.util.List;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Integer> {

    TicketHistory findById(int id);

    List<TicketHistory> findAllByOrderById();
    List<TicketHistory> findByTicketOrderById(Ticket ticket);

    Page<TicketHistory> findAllByOrderById(Pageable pageable);
    Page<TicketHistory> findByTicketOrderById(Ticket ticket, Pageable pageable);

}
