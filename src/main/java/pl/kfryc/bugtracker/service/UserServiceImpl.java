package pl.kfryc.bugtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kfryc.bugtracker.dao.RoleRepository;
import pl.kfryc.bugtracker.dao.UserRepository;
import pl.kfryc.bugtracker.entity.AuthUserDetails;
import pl.kfryc.bugtracker.entity.Role;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.UserRole;
import pl.kfryc.bugtracker.user.BugTrackerUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${default.profile.pic}")
    private String defaultProfilePic;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User findUserById(int userId) {
        return userRepository.findUserById(userId);
    }


    @Override
    @Transactional
    public Optional<Role> findRoleById(int roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    @Transactional
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(BugTrackerUser bugTrackerUser) {
        User user = new User();

        // assigning user details to the user object
        user.setFirstName(bugTrackerUser.getFirstName());
        user.setLastName(bugTrackerUser.getLastName());
        user.setEmail(bugTrackerUser.getEmail());
        user.setProfilePic(defaultProfilePic);
        user.setPassword(passwordEncoder.encode(bugTrackerUser.getPassword()));

        //User registers as Submitter role in the beginning
        user.setIdRole(4);
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return loadUser(username);
    }

    private AuthUserDetails loadUser(String email) throws UsernameNotFoundException{

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or Password");
        }

        return new AuthUserDetails(user, mapRoleToAuthorities(roleRepository.findById(user.getIdRole())), true);
    }


    @Override
    @Transactional
    public List<UserRole> findAllUserRole() {
        return userRepository.findAllUserRole();
    }

    @Override
    public List<UserRole> findUserRoleByProjectId(int projectId) {
        return userRepository.findUserRoleByProjectId(projectId);
    }

    @Override
    public Role findRoleByIdRole(int roleId) {
        return roleRepository.findRoleById(roleId);
    }

    // For now users can have only one role at the time.

    public Collection<? extends GrantedAuthority> mapRoleToAuthorities(Optional<Role> JpaRoles) {
        List<GrantedAuthority> authority = new ArrayList<>();
        Role role = JpaRoles.get();
        authority.add(new SimpleGrantedAuthority(role.getName()));
        return authority;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

}
