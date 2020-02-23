package pl.kfryc.bugtracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.ticket.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TicketService {

    Ticket findById(int id);

    TicketType findTypeById(int id);

    TicketPriority findPriorityById(int id);

    TicketStatus findStatusById(int id);

    List<Ticket> findAll();

    List<TicketStatus> findAllTicketStatus();

    List<TicketPriority> findAllTicketPriority();

    List<TicketType> findAllTicketType();

    Page<Ticket> findAll(Pageable pageable);

    void save(Ticket ticket);

    // Additional functions

    List<Ticket> findByDeveloper(User user);

    Page<Ticket> findByDeveloper(User user, Pageable pageable);

    List<Ticket> findBySubmitter(User user);

    Page<Ticket> findBySubmitter(User user, Pageable pageable);

    List<Ticket> findByProject(Project project);

    Page<Ticket> findByProject(Project project, Pageable pageable);


    TicketComment findTicketCommentById(int id);

    List<TicketComment> findAllCommentsByOrderById();

    List<TicketComment> findCommentsByTicketOrderById(Ticket ticket);

    Page<TicketComment> findAllCommentsByOrderById(Pageable pageable);

    Page<TicketComment> findCommentsByTicketOrderById(Ticket ticket, Pageable pageable);

    List<Ticket> findByProjectOrderById(HashSet<Project> project);

    TicketHistory findHistoryById(int id);

    List<TicketHistory> findAllByOrderById();
    List<TicketHistory> findByTicketOrderById(Ticket ticket);

    Page<TicketHistory> findAllByOrderById(Pageable pageable);
    Page<TicketHistory> findByTicketOrderById(Ticket ticket, Pageable pageable);

    TicketFiles findFileById(int id);

    List<TicketFiles> findAllFilesByOrderById();
    List<TicketFiles> findFilesByTicketOrderById(Ticket ticket);

    void save(TicketComment comment);

    void saveHistory(TicketHistory ticketHistory);

    void saveFile(TicketFiles ticketFiles);


}
