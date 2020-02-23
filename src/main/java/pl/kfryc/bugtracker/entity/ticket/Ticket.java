package pl.kfryc.bugtracker.entity.ticket;


import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private TicketPriority ticketPriority;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "submitter_id")
    private User submitter;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private User developer;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @OneToMany(mappedBy = "ticket")
    private Set<TicketComment> comments;

    @OneToMany(mappedBy = "ticket")
    private Set<TicketHistory> history;

    @OneToMany(mappedBy = "ticket")
    private Set<TicketFiles> files;

    // Constructors


    public Ticket() {
    }

    public Ticket(String title, String description, Project project, TicketType ticketType, TicketPriority ticketPriority, TicketStatus ticketStatus, User submitter, Date created) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.ticketType = ticketType;
        this.ticketPriority = ticketPriority;
        this.ticketStatus = ticketStatus;
        this.submitter = submitter;
        this.created = created;
    }

    public Ticket(String title, String description, Project project, TicketType ticketType, TicketPriority ticketPriority, TicketStatus ticketStatus, User submitter, User developer, Date created) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.ticketType = ticketType;
        this.ticketPriority = ticketPriority;
        this.ticketStatus = ticketStatus;
        this.submitter = submitter;
        this.developer = developer;
        this.created = created;
    }

    public Ticket(String title, String description, Project project, TicketType ticketType, TicketPriority ticketPriority, TicketStatus ticketStatus, User submitter, User developer, Date created, Date updated) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.ticketType = ticketType;
        this.ticketPriority = ticketPriority;
        this.ticketStatus = ticketStatus;
        this.submitter = submitter;
        this.developer = developer;
        this.created = created;
        this.updated = updated;
    }

    public Ticket(String title, String description, Project project, TicketType ticketType, TicketPriority ticketPriority, TicketStatus ticketStatus, User submitter, User developer, Date created, Date updated, Set<TicketComment> comments, Set<TicketHistory> history) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.ticketType = ticketType;
        this.ticketPriority = ticketPriority;
        this.ticketStatus = ticketStatus;
        this.submitter = submitter;
        this.developer = developer;
        this.created = created;
        this.updated = updated;
        this.comments = comments;
        this.history = history;
    }

    // Functions


    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<TicketComment> getComments() {
        return comments;
    }

    public void setComments(Set<TicketComment> comments) {
        this.comments = comments;
    }

    public Set<TicketHistory> getHistory() {
        return history;
    }

    public void setHistory(Set<TicketHistory> history) {
        this.history = history;
    }

    public Set<TicketFiles> getFiles() {
        return files;
    }

    public void setFiles(Set<TicketFiles> files) {
        this.files = files;
    }
}

