package pl.kfryc.bugtracker.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kfryc.bugtracker.entity.Role;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.UserRole;
import pl.kfryc.bugtracker.user.BugTrackerUser;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    User findUserById(int userId);
    Optional<Role> findRoleById(int roleId);
    Role findRoleByIdRole(int roleId);

    List<Role> findAllRoles();

    void save(BugTrackerUser bugTrackerUser);
    void save(User user);

    List<User> findAllUsers();

    List<UserRole> findAllUserRole();

    List<UserRole> findUserRoleByProjectId(int projectId);

    Collection<? extends GrantedAuthority> mapRoleToAuthorities(Optional<Role> JpaRoles);

    BCryptPasswordEncoder getPasswordEncoder();

}
