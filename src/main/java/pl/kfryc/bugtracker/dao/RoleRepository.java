package pl.kfryc.bugtracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleById(int roleID);

}
