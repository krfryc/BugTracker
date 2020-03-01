package pl.kfryc.bugtracker.service;

import pl.kfryc.bugtracker.entity.Project;

import java.util.List;

public interface ProjectService {

    Project findById(int id);

    List<Project> findAll();

    void save(Project project);

}
