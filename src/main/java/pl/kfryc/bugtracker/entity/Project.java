package pl.kfryc.bugtracker.entity;


import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.entity.ticket.Ticket;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "user_project",
                joinColumns = @JoinColumn(name = "project_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "project")
    private Set<Ticket> tickets;


    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new HashSet<>();
    }

    public Project(String name, String description, HashSet<User> users) {
        this.name = name;
        this.description = description;
        this.users = users;
    }

    public Project(String name, String description, Set<User> users, Set<Ticket> tickets) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.tickets = tickets;
    }

    public void addUser(User user){
        users.add(user);
    }

}
