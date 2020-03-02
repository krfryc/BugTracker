package pl.kfryc.bugtracker.entity;

import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketComment;
import pl.kfryc.bugtracker.entity.ticket.TicketFiles;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "idRole")
    private int idRole;

    @Column(name = "profile_pic")
    private String profilePic;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    @OneToMany(mappedBy = "submitter")
    private Set<Ticket> submitter;

    @OneToMany(mappedBy = "developer")
    private Set<Ticket> developer;

    @OneToMany(mappedBy = "user")
    private Set<TicketComment> comments;

    @OneToMany(mappedBy = "user")
    private Set<TicketFiles> files;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, int idRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.idRole = idRole;
        this.projects = new HashSet<>();
    }

    public User(String firstName, String lastName, String email, String password, int idRole, HashSet<Project> projects, String profilePic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.idRole = idRole;
        this.projects = projects;
        this.profilePic = profilePic;
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public void removeProject(Project project){
        projects.remove(project);
    }

    public String getProjectsIdToString(){
        List<Integer> array = new ArrayList<>();
        if(!projects.isEmpty()){
            projects.forEach(project -> array.add(project.getId()));
            return array.toString();
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || (getClass() !=obj.getClass() && obj.getClass() != UserRole.class)) return false;
        return this.id == ((UserRole) obj).getId();
    }

}
