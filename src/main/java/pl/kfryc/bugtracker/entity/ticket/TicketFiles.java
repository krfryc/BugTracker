package pl.kfryc.bugtracker.entity.ticket;


import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket_attachments")
@Getter
@Setter
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

    public TicketFiles() {
    }

    public TicketFiles(Ticket ticket, String fileName, String note, User user, Date created) {
        this.ticket = ticket;
        this.fileName = fileName;
        this.note = note;
        this.user = user;
        this.created = created;
    }

}
