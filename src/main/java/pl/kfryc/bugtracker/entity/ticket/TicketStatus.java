package pl.kfryc.bugtracker.entity.ticket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_status")
@Getter
@Setter
public class TicketStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketStatus")
    private Set<Ticket> ticket;

    public TicketStatus() {
    }

    public TicketStatus(String name) {
        this.name = name;
    }

    public TicketStatus(String name, Set<Ticket> ticket) {
        this.name = name;
        this.ticket = ticket;
    }

}
