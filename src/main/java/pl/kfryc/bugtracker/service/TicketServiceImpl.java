package pl.kfryc.bugtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    // Find by Id

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

    // Find all list functions

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


    // Find all pageable functions
    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAllByOrderById(pageable);
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
    public Page<Ticket> findByDeveloper(User user, Pageable pageable) {
        return ticketRepository.findByDeveloperOrderById(user, pageable);
    }

    @Override
    public List<Ticket> findBySubmitter(User user) {
        return ticketRepository.findBySubmitterOrderById(user);
    }

    @Override
    public Page<Ticket> findBySubmitter(User user, Pageable pageable) {
        return ticketRepository.findBySubmitterOrderById(user, pageable);
    }

    @Override
    public List<Ticket> findByProject(Project project) {
        return ticketRepository.findByProjectOrderById(project);
    }

    @Override
    public Page<Ticket> findByProject(Project project, Pageable pageable) {
        return ticketRepository.findByProjectOrderById(project, pageable);
    }

    @Override
    public TicketComment findTicketCommentById(int id) {
        return ticketCommentRepository.findById(id);
    }

    @Override
    public List<TicketComment> findAllCommentsByOrderById() {
        return ticketCommentRepository.findAllByOrderById();
    }

    @Override
    public List<TicketComment> findCommentsByTicketOrderById(Ticket ticket) {
        return ticketCommentRepository.findByTicketOrderById(ticket);
    }

    @Override
    public Page<TicketComment> findAllCommentsByOrderById(Pageable pageable) {
        return ticketCommentRepository.findAllByOrderById(pageable);
    }

    @Override
    public Page<TicketComment> findCommentsByTicketOrderById(Ticket ticket, Pageable pageable) {
        return ticketCommentRepository.findByTicketOrderById(ticket, pageable);
    }

    @Override
    public List<Ticket> findByProjectOrderById(HashSet<Project> projects) {
        ArrayList<Ticket> list = new ArrayList<>();
        projects.forEach(project -> list.addAll(ticketRepository.findByProjectOrderById(project)));
        return list;
    }

    @Override
    public TicketHistory findHistoryById(int id) {
        return ticketHistoryRepository.findById(id);
    }

    @Override
    public List<TicketHistory> findAllByOrderById() {
        return ticketHistoryRepository.findAllByOrderById();
    }

    @Override
    public List<TicketHistory> findByTicketOrderById(Ticket ticket) {
        return ticketHistoryRepository.findByTicketOrderById(ticket);
    }

    @Override
    public Page<TicketHistory> findAllByOrderById(Pageable pageable) {
        return ticketHistoryRepository.findAllByOrderById(pageable);
    }

    @Override
    public Page<TicketHistory> findByTicketOrderById(Ticket ticket, Pageable pageable) {
        return ticketHistoryRepository.findByTicketOrderById(ticket, pageable);
    }

    @Override
    public TicketFiles findFileById(int id) {
        return ticketFileRepository.findById(id);
    }

    @Override
    public List<TicketFiles> findAllFilesByOrderById() {
        return ticketFileRepository.findAllByOrderById();
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
