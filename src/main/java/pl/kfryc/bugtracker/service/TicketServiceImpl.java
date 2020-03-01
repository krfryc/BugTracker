package pl.kfryc.bugtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kfryc.bugtracker.dao.ticket.*;
import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.ticket.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;

    @Autowired
    private TicketFileRepository ticketFileRepository;

    @Override
    public Ticket findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public TicketType findTypeById(int id) {
        return ticketTypeRepository.findById(id);
    }

    @Override
    public TicketPriority findPriorityById(int id) {
        return ticketPriorityRepository.findById(id);
    }

    @Override
    public TicketStatus findStatusById(int id) {
        return ticketStatusRepository.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAllByOrderById();
    }

    @Override
    public List<TicketStatus> findAllTicketStatus() {
        return ticketStatusRepository.findAllByOrderById();
    }

    @Override
    public List<TicketPriority> findAllTicketPriority() {
        return ticketPriorityRepository.findAllByOrderById();
    }

    @Override
    public List<TicketType> findAllTicketType() {
        return ticketTypeRepository.findAllByOrderById();
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findByDeveloper(User user) {
        return ticketRepository.findByDeveloperOrderById(user);
    }

    @Override
    public List<Ticket> findBySubmitter(User user) {
        return ticketRepository.findBySubmitterOrderById(user);
    }


    @Override
    public List<Ticket> findByProject(Project project) {
        return ticketRepository.findByProjectOrderById(project);
    }

    @Override
    public List<TicketComment> findCommentsByTicketOrderById(Ticket ticket) {
        return ticketCommentRepository.findByTicketOrderById(ticket);
    }

    @Override
    public List<Ticket> findByProjectOrderById(HashSet<Project> projects) {
        ArrayList<Ticket> list = new ArrayList<>();
        projects.forEach(project -> list.addAll(ticketRepository.findByProjectOrderById(project)));
        return list;
    }

    @Override
    public List<TicketHistory> findByTicketOrderById(Ticket ticket) {
        return ticketHistoryRepository.findByTicketOrderById(ticket);
    }

    @Override
    public TicketFiles findFileById(int id) {
        return ticketFileRepository.findById(id);
    }

    @Override
    public List<TicketFiles> findFilesByTicketOrderById(Ticket ticket) {
        return ticketFileRepository.findByTicketOrderById(ticket);
    }

    @Override
    public void save(TicketComment comment) {
        ticketCommentRepository.save(comment);
    }

    @Override
    public void saveHistory(TicketHistory ticketHistory) {
        ticketHistoryRepository.save(ticketHistory);
    }

    @Override
    public void saveFile(TicketFiles ticketFiles) {
        ticketFileRepository.save(ticketFiles);
    }
}
