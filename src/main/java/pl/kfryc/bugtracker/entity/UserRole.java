package pl.kfryc.bugtracker.entity;


import java.util.Objects;

// helper class used to store User and Role left join SQL query
public class UserRole {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;

    public UserRole(int id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleName = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("in UserRole equals");
        if(this == obj) return true;
        if(obj == null || (getClass() !=obj.getClass() && obj.getClass() != User.class)){
            return false;
        }
        return Objects.equals(this.getId(), ((User) obj).getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String role) {
        this.roleName = role;
    }

}
