package pl.kfryc.bugtracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kfryc.bugtracker.entity.Project;

import java.util.List;

public interface ProjectService {

    Project findById(int id);

    List<Project> findAll();

    void save(Project project);

    Page<Project> findAll(Pageable pageable);
}
