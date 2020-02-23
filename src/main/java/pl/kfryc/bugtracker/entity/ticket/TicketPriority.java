package pl.kfryc.bugtracker.entity.ticket;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_priority")
public class TicketPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketPriority")
    private Set<Ticket> ticket;

    // == constructors ==

    public TicketPriority() {
    }

    public TicketPriority(String name) {
        this.name = name;
    }

    public TicketPriority(String name, Set<Ticket> ticket) {
        this.name = name;
        this.ticket = ticket;
    }

// == getters and setters ==


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(Set<Ticket> ticket) {
        this.ticket = ticket;
    }
}
