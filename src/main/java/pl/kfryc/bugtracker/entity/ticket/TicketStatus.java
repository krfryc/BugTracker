package pl.kfryc.bugtracker.entity.ticket;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_status")
public class TicketStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketStatus")
    private Set<Ticket> ticket;

    // == constructors ==

    public TicketStatus() {
    }

    public TicketStatus(String name) {
        this.name = name;
    }

    public TicketStatus(String name, Set<Ticket> ticket) {
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
