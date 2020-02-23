package pl.kfryc.bugtracker.entity;


import java.util.*;

// Helper class to gather data for charts
public class TicketChartData {

    public static class TypesData {
        public int id;
        public String name;
        public int count = 0;

        // constructors
        public TypesData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Functions
        public void addToCount() {
            count++;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            else {
                TypesData object = (TypesData) obj;
                if (this.name == object.name) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    public static class StatusData {
        public int id;
        public String name;
        public int count = 0;

        // constructors
        public StatusData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Functions
        public void addToCount() {
            count++;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            else {
                StatusData object = (StatusData) obj;
                if (this.name == object.name) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

    public static class PriorityData {
        public int id;
        public String name;
        public int count = 0;

        // constructors
        public PriorityData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Functions
        public void addToCount() {
            count++;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            else {
                PriorityData object = (PriorityData) obj;
                if (this.name == object.name) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

    public static class ProjectData {
        public int id;
        public String name;
        public int count = 0;

        // constructors
        public ProjectData(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Functions
        public void addToCount() {
            count++;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            else {
                ProjectData object = (ProjectData) obj;
                if (this.name == object.name) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

    private Set<TypesData> types;
    private Set<StatusData> statuses;
    private Set<PriorityData> priorities;
    private Set<ProjectData> projects;


    // Constructors

    public TicketChartData() {
        types = new HashSet<>();
        statuses = new HashSet<>();
        priorities = new HashSet<>();
        projects = new HashSet<>();
    }

    // Functions
    public List<String> listTypes() {
        List<String> list = new ArrayList<>();
        List<TypesData> tempList = new ArrayList<>(types);
        tempList.sort(new Comparator<TypesData>() {
            @Override
            public int compare(TypesData o1, TypesData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(typesData -> list.add(typesData.name));
        return list;
    }

    public List<Integer> countTypes() {
        List<Integer> list = new ArrayList<>();

        List<TypesData> tempList = new ArrayList<>(types);
        tempList.sort(new Comparator<TypesData>() {
            @Override
            public int compare(TypesData o1, TypesData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(typesData -> list.add(typesData.count));
        return list;
    }


    public List<String> listStatuses() {
        List<String> list = new ArrayList<>();
        List<StatusData> tempList = new ArrayList<>(statuses);
        tempList.sort(new Comparator<StatusData>() {
            @Override
            public int compare(StatusData o1, StatusData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(statusData -> list.add(statusData.name));
        return list;
    }

    public List<Integer> countStatuses() {
        List<Integer> list = new ArrayList<>();
        List<StatusData> tempList = new ArrayList<>(statuses);
        tempList.sort(new Comparator<StatusData>() {
            @Override
            public int compare(StatusData o1, StatusData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(statusData -> list.add(statusData.count));
        return list;
    }

    public List<String> listPriorities() {
        List<String> list = new ArrayList<>();
        List<PriorityData> tempList = new ArrayList<>(priorities);
        tempList.sort(new Comparator<PriorityData>() {
            @Override
            public int compare(PriorityData o1, PriorityData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(priorityData -> list.add(priorityData.name));
        return list;
    }

    public List<Integer> countPriorities() {
        List<Integer> list = new ArrayList<>();
        List<PriorityData> tempList = new ArrayList<>(priorities);
        tempList.sort(new Comparator<PriorityData>() {
            @Override
            public int compare(PriorityData o1, PriorityData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(priorityData -> list.add(priorityData.count));
        return list;
    }

    public List<String> listProjects() {
        List<String> list = new ArrayList<>();
        List<ProjectData> tempList = new ArrayList<>(projects);
        tempList.sort(new Comparator<ProjectData>() {
            @Override
            public int compare(ProjectData o1, ProjectData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(projectData -> list.add(projectData.name));
        return list;
    }

    public List<Integer> countProjects() {
        List<Integer> list = new ArrayList<>();
        List<ProjectData> tempList = new ArrayList<>(projects);
        tempList.sort(new Comparator<ProjectData>() {
            @Override
            public int compare(ProjectData o1, ProjectData o2) {
                return o1.id-o2.id;
            }
        });
        tempList.forEach(projectData -> list.add(projectData.count));
        return list;
    }

    public int getMaxTypes() {
        return Collections.max(countTypes());
    }


    public int getMaxStatuses() {
        return Collections.max(countStatuses());
    }


    public int getMaxPriorities() {
        return Collections.max(countPriorities());
    }


    public int getMaxProjects() {
        return Collections.max(countProjects());
    }

    // Getters and setters


    public Set<TypesData> getTypes() {
        return types;
    }

    public void setTypes(Set<TypesData> types) {
        this.types = types;
    }

    public Set<StatusData> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<StatusData> statuses) {
        this.statuses = statuses;
    }

    public Set<PriorityData> getPriorities() {
        return priorities;
    }

    public void setPriorities(Set<PriorityData> priorities) {
        this.priorities = priorities;
    }

    public Set<ProjectData> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectData> projects) {
        this.projects = projects;
    }
}
