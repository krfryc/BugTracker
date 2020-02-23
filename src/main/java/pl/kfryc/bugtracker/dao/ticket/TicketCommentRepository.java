package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketComment;
import pl.kfryc.bugtracker.entity.ticket.TicketPriority;

import java.util.List;

public interface TicketCommentRepository extends JpaRepository<TicketComment, Integer> {

    TicketComment findById(int id);

    List<TicketComment> findAllByOrderById();
    List<TicketComment> findByTicketOrderById(Ticket ticket);

    Page<TicketComment> findAllByOrderById(Pageable pageable);
    Page<TicketComment> findByTicketOrderById(Ticket ticket, Pageable pageable);
}
