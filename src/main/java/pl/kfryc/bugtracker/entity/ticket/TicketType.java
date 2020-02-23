package pl.kfryc.bugtracker.entity.ticket;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_type")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketType")
    private Set<Ticket> ticket;


    // == constructors ==

    public TicketType() {
    }

    public TicketType(String name) {
        this.name = name;
    }

    public TicketType(String name, Set<Ticket> ticket) {
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
