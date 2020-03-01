package pl.kfryc.bugtracker.service;

import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.ticket.*;

import java.util.HashSet;
import java.util.List;

public interface TicketService {

    Ticket findById(int id);

    TicketType findTypeById(int id);

    TicketPriority findPriorityById(int id);

    TicketStatus findStatusById(int id);

    List<Ticket> findAll();

    List<TicketStatus> findAllTicketStatus();

    List<TicketPriority> findAllTicketPriority();

    List<TicketType> findAllTicketType();

    void save(Ticket ticket);

    List<Ticket> findByDeveloper(User user);

    List<Ticket> findBySubmitter(User user);

    List<Ticket> findByProject(Project project);


    List<TicketComment> findCommentsByTicketOrderById(Ticket ticket);


    List<Ticket> findByProjectOrderById(HashSet<Project> project);
    List<TicketHistory> findByTicketOrderById(Ticket ticket);


    TicketFiles findFileById(int id);

    List<TicketFiles> findFilesByTicketOrderById(Ticket ticket);

    void save(TicketComment comment);

    void saveHistory(TicketHistory ticketHistory);

    void saveFile(TicketFiles ticketFiles);


}
