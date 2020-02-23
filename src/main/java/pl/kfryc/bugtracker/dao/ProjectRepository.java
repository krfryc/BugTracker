package pl.kfryc.bugtracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kfryc.bugtracker.entity.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findById(int id);

    List<Project> findAllByOrderByName();

    Page<Project> findAllByOrderByName(Pageable pageable);
}
