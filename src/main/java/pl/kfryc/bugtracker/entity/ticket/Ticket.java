package pl.kfryc.bugtracker.entity.ticket;


import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.entity.Project;
import pl.kfryc.bugtracker.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tickets")
@Getter
@Setter
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
}

