package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.TicketPriority;

import java.util.List;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {

    TicketPriority findById(int id);

    List<TicketPriority> findAllByOrderById();

    Page<TicketPriority> findAllByOrderById(Pageable pageable);
}
