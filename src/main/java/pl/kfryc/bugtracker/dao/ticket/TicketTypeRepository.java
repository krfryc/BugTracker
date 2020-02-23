package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.ticket.TicketType;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {

    TicketType findById(int id);

    List<TicketType> findAllByOrderById();

    Page<TicketType> findAllByOrderById(Pageable pageable);
}
