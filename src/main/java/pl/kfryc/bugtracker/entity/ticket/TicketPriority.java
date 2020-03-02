package pl.kfryc.bugtracker.entity.ticket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_priority")
@Getter
@Setter
public class TicketPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketPriority")
    private Set<Ticket> ticket;

    public TicketPriority() {
    }

    public TicketPriority(String name) {
        this.name = name;
    }

    public TicketPriority(String name, Set<Ticket> ticket) {
        this.name = name;
        this.ticket = ticket;
    }

}
