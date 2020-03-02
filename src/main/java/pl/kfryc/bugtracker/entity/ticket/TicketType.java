package pl.kfryc.bugtracker.entity.ticket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_type")
@Getter
@Setter
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ticketType")
    private Set<Ticket> ticket;


    public TicketType() {
    }

    public TicketType(String name) {
        this.name = name;
    }

    public TicketType(String name, Set<Ticket> ticket) {
        this.name = name;
        this.ticket = ticket;
    }

}
