package pl.kfryc.bugtracker.dao.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketComment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Ticket findById(int id);

    List<Ticket> findAllByOrderById();

    Page<Ticket> findAllByOrderById(Pageable pageable);

    List<Ticket> findByDeveloperOrderById(User user);

    Page<Ticket> findByDeveloperOrderById(User user, Pageable pageable);

    List<Ticket> findBySubmitterOrderById(User user);

    Page<Ticket> findBySubmitterOrderById(User user, Pageable pageable);

    List<Ticket> findByProjectOrderById(Project project);

    Page<Ticket> findByProjectOrderById(Project project, Pageable pageable);

    void save(TicketComment ticketComment);

}
