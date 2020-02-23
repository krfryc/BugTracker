package pl.kfryc.bugtracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.entity.UserRole;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findUserById(int userId);

    @Query("select new pl.kfryc.bugtracker.entity.UserRole(u.id, u.firstName, u.lastName, u.email, r.name) " +
            "from User u " +
            "inner join " +
            "Role r " +
            "on u.idRole = r.id " +
            "order by u.lastName")
    
    Page<UserRole> findAllUserRole(Pageable pageable);

    @Query("select new pl.kfryc.bugtracker.entity.UserRole(u.id, u.firstName, u.lastName, u.email, r.name) " +
            "from User u " +
            "inner join " +
            "Role r " +
            "on u.idRole = r.id " +
            "order by u.lastName")
    List<UserRole> findAllUserRole();

    @Query("select new pl.kfryc.bugtracker.entity.UserRole(u.id, u.firstName, u.lastName, u.email, r.name) " +
            "from User u " +
            "inner join Role r " +
            "on u.idRole = r.id " +
            "join u.projects p " +
            "where p.id=:projectId " +
            "order by u.lastName")
    Page<UserRole> findUserRoleByProjectId(@Param("projectId") int projectId, Pageable pageable);

    @Query("select new pl.kfryc.bugtracker.entity.UserRole(u.id, u.firstName, u.lastName, u.email, r.name) " +
            "from User u " +
            "inner join Role r " +
            "on u.idRole = r.id " +
            "join u.projects p " +
            "where p.id = :projectId " +
            "order by u.lastName")
    List<UserRole> findUserRoleByProjectId(@Param("projectId") int projectId);

}
