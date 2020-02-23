package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.TicketStatus;

import java.util.List;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {

    TicketStatus findById(int id);

    List<TicketStatus> findAllByOrderById();

    Page<TicketStatus> findAllByOrderById(Pageable pageable);
}
