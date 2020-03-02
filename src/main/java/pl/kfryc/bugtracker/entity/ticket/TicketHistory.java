package pl.kfryc.bugtracker.entity.ticket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "created")
    private Date created;

    public TicketHistory() {
    }

    public TicketHistory(Ticket ticket, String propertyName, String oldValue, String newValue, Date created) {
        this.ticket = ticket;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.created = created;
    }

}
