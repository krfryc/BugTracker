package pl.kfryc.bugtracker.entity.ticket;


import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket_comments")
@Getter
@Setter
public class TicketComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created")
    private Date created;


    public TicketComment() {
    }

    public TicketComment(Ticket ticket, String message, User user, Date created) {
        this.ticket = ticket;
        this.message = message;
        this.user = user;
        this.created = created;
    }

}
