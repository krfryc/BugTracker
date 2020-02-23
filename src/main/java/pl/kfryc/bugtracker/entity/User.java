package pl.kfryc.bugtracker.entity;

import org.springframework.beans.factory.annotation.Value;
import pl.kfryc.bugtracker.entity.ticket.Ticket;
import pl.kfryc.bugtracker.entity.ticket.TicketComment;
import pl.kfryc.bugtracker.entity.ticket.TicketFiles;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
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


    // == Constructors ==


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

    // == Functions ==

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

    // == Equal / HashCode ==

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

    // == getters/setters ==


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(HashSet<Project> projects) {
        this.projects = projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Ticket> getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Set<Ticket> submitter) {
        this.submitter = submitter;
    }

    public Set<Ticket> getDeveloper() {
        return developer;
    }

    public void setDeveloper(Set<Ticket> developer) {
        this.developer = developer;
    }

    public Set<TicketComment> getComments() {
        return comments;
    }

    public void setComments(Set<TicketComment> comments) {
        this.comments = comments;
    }

    public Set<TicketFiles> getFiles() {
        return files;
    }

    public void setFiles(Set<TicketFiles> files) {
        this.files = files;
    }


}
