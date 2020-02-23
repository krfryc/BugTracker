package pl.kfryc.bugtracker.entity;


import java.util.Comparator;

public class ProjectSort implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
