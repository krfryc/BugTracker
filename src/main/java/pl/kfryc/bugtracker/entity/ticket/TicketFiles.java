package pl.kfryc.bugtracker.entity.ticket;


import pl.kfryc.bugtracker.entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket_attachments")
public class TicketFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "notes")
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created")
    private Date created;

    // Constructors

    public TicketFiles() {
    }

    public TicketFiles(Ticket ticket, String fileName, String note, User user, Date created) {
        this.ticket = ticket;
        this.fileName = fileName;
        this.note = note;
        this.user = user;
        this.created = created;
    }

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
