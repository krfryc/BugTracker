package pl.kfryc.bugtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.component.RoleCheckInterceptor;
import pl.kfryc.bugtracker.component.UserChangeInterceptor;
import pl.kfryc.bugtracker.config.StorageProperties;
import pl.kfryc.bugtracker.entity.*;
import pl.kfryc.bugtracker.entity.ticket.*;
import pl.kfryc.bugtracker.exception.FileNotFoundException;
import pl.kfryc.bugtracker.service.ProjectService;
import pl.kfryc.bugtracker.service.StorageService;
import pl.kfryc.bugtracker.service.TicketService;
import pl.kfryc.bugtracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class BugTrackerController {

    private UserService userService;

    private ProjectService projectService;

    private TicketService ticketService;

    private StorageService storageService;

    private StorageProperties storageProperties;

    @Autowired
    public BugTrackerController(UserService userService,
                                ProjectService projectService,
                                TicketService ticketService,
                                StorageService storageService,
                                StorageProperties storageProperties) {
        this.userService = userService;
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.storageService = storageService;
        this.storageProperties = storageProperties;
    }

    // main page after login
    @GetMapping("/")
    public String indexPage(Model model){
        User user = getUser();
        List<Ticket> tickets = ticketsListSetup(user);

        TicketChartData ticketChartData = new TicketChartData();

        if(!tickets.isEmpty()) {

            tickets.forEach(ticket -> {
                ticketChartData.getTypes().add(new TicketChartData.TypesData(ticket.getTicketType().getId(), ticket.getTicketType().getName()));
                ticketChartData.getTypes().forEach(typesData -> {
                    if (typesData.name.equals(ticket.getTicketType().getName())) {
                        typesData.addToCount();
                    }
                });

                ticketChartData.getStatuses().add(new TicketChartData.StatusData(ticket.getTicketStatus().getId(), ticket.getTicketStatus().getName()));
                ticketChartData.getStatuses().forEach(statusData -> {
                    if (statusData.name.equals(ticket.getTicketStatus().getName())) {
                        statusData.addToCount();
                    }
                });

                ticketChartData.getPriorities().add(new TicketChartData.PriorityData(ticket.getTicketPriority().getId(), ticket.getTicketPriority().getName()));
                ticketChartData.getPriorities().forEach(priorityData -> {
                    if (priorityData.name.equals(ticket.getTicketPriority().getName())) {
                        priorityData.addToCount();
                    }
                });

                ticketChartData.getProjects().add(new TicketChartData.ProjectData(ticket.getProject().getId(), ticket.getProject().getName()));
                ticketChartData.getProjects().forEach(projectData -> {
                    if (projectData.name.equals(ticket.getProject().getName())) {
                        projectData.addToCount();
                    }
                });
            });
            model.addAttribute("chartData", ticketChartData);
        } else {
            model.addAttribute("chartData", null);
        }

        return "index";
    }

    @GetMapping("/manage-roles")
    public String manageRoles(Model model) {


        List<UserRole> list = userService.findAllUserRole();

        model.addAttribute("list", list);

        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);


        return "manage-roles";

    }

    @GetMapping("/user")
    public String userProfile(Model model) {

        User user = getUser();
        model.addAttribute("user", user);
        Role role = userService.findRoleByIdRole(user.getIdRole());
        model.addAttribute("role", role.getName());


        return "user-profile";
    }

    @PostMapping("/change-profile-pic")
    public String changeProfilePic(@RequestParam("uploads/profilePic") MultipartFile file) {
        User user = getUser();
        user.setProfilePic(storageService.storeProfilePic(file, user));

        userService.save(user);

        UserChangeInterceptor.users_to_update.add(user.getEmail());

        return "redirect:/user";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("password") String password) {
        User user = getUser();
        boolean successChange;

        if(userService.getPasswordEncoder().matches(oldPassword, user.getPassword())){
            user.setPassword(userService.getPasswordEncoder().encode(password));
            userService.save(user);
            successChange = true;
        } else {
            successChange = false;
        }

        return "redirect:/user?passwordChange="+successChange;
    }


    @PostMapping("/manage-roles-update")
    public String updateRoles(@RequestParam("users") HashSet<Integer> usersId,
                              @RequestParam("role") int roleId) {

        usersId.forEach(userId -> {
            User user = userService.findUserById(userId);
            user.setIdRole(roleId);
            userService.save(user);

            // Refresh authorities if their role has changed and they are logged in
            RoleCheckInterceptor.users_to_update_roles.add(user.getEmail());

        });

        return "redirect:/manage-roles";
    }


    @GetMapping("/manage-project-users")
    public String manageProjectUsers(Model model) {

        List<Project> projects = projectsListSetup(getUser());

        model.addAttribute("projects", projects);
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);


        return "manage-project-users";

    }


    @PostMapping("/manage-project-users-update")
    public String updateProjectUsers(@RequestParam("users") HashSet<Integer> usersId,
                                     @RequestParam("project") int projectId) {

        usersId.forEach(userId -> {
            User user = userService.findUserById(userId);
            user.addProject(projectService.findById(projectId));

            userService.save(user);

        });

        return "redirect:/manage-project-users";
    }

    @PostMapping("/manage-project-users-delete")
    public String deleteProjectUsers(@RequestParam("users") HashSet<Integer> usersId,
                                     @RequestParam("project") int projectId) {

        usersId.forEach(userId -> {
            User user = userService.findUserById(userId);
            user.removeProject(projectService.findById(projectId));

            userService.save(user);

        });

        return "redirect:/manage-project-users";
    }


    // List all projects

    @GetMapping("/projects")
    public String myProjects(Model model) {

        User user = getUser();
        List<Project> projects = projectsListSetup(user);
        model.addAttribute("list", projects);

        return "projects";

    }

    @GetMapping("/project-details")
    public String projectDetails(@RequestParam("projectId") int projectId,
                                 Model model) {

        Project project = projectService.findById(projectId);

        User user = getUser();

        // Check if user is eligible to see project (Admin can see all projects)
        if (user.getProjects().contains(project) || user.getIdRole() == 1) {
            model.addAttribute("project", project);

            List<UserRole> projectUserRoleList = userService.findUserRoleByProjectId(projectId);
            model.addAttribute("list", projectUserRoleList);

            List<Ticket> projectTickets = ticketsPerProjectListSetup(user, project);
            model.addAttribute("listTickets", projectTickets);


            return "project-details";
        }

        return myProjects(model);

    }


    // GetMapping to edit single project

    @GetMapping("/project")
    public String getEditProject(@RequestParam("projectId") int projectId, HttpServletRequest request, Model model) {
        Project project = projectService.findById(projectId);
        User user = getUser();

        // Check if user is eligible to edit project (Admin and Project Manager)
        if (user.getIdRole() == 1 || (user.getIdRole() == 2 && user.getProjects().contains(project)) && project != null) {
            model.addAttribute("project", project);

            return "edit-project";
        }

        return myProjects(model);

    }

    @GetMapping("/new-project")
    public String newProject(Model model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "new-project";

    }

    @PostMapping("/project-save")
    public String saveProject(@RequestParam("users") HashSet<Integer> usersId,
                              @RequestParam("name") String projectName,
                              @RequestParam("description") String description) {

        Project project = new Project(projectName, description);
        projectService.save(project);

        // Adding current user to the list - project creator (if he forgets to add himself)
        usersId.add(getUser().getId());

        usersId.forEach(userId -> {
            User user = userService.findUserById(userId);
            user.addProject(project);
            userService.save(user);

        });

        return "redirect:/projects";
    }

    @PostMapping("/project-save-edit")
    public String saveEditProject(@RequestParam("id") int projectId,
                                  @RequestParam("name") String projectName,
                                  @RequestParam("description") String description) {

        Project project = projectService.findById(projectId);
        project.setName(projectName);
        project.setDescription(description);
        projectService.save(project);

        return "redirect:/projects";
    }

    // My Tickets

    @GetMapping("/tickets")
    public String myTickets(Model model) {

        List<Ticket> tickets = ticketsListSetup(getUser());
        model.addAttribute("list", tickets);

        return "tickets";

    }

    @GetMapping("/ticket-details")
    public String ticketDetails(@RequestParam("ticketId") int ticketId,
                                Model model) {

        Ticket ticket = ticketService.findById(ticketId);

        User user = getUser();
        List<Ticket> tickets = ticketsListSetup(user);

        // check if the user is assigned to the project and to the ticket based on role or the ticket is unassigned but the person is in the project
        if (tickets.contains(ticket) || (ticket.getDeveloper()==null && ticket.getProject().getUsers().contains(user))) {
            model.addAttribute("ticket", ticket);
            model.addAttribute("user", user);
            model.addAttribute("list", ticketService.findCommentsByTicketOrderById(ticket));
            model.addAttribute("historyList", ticketService.findByTicketOrderById(ticket));
            model.addAttribute("uploadList", ticketService.findFilesByTicketOrderById(ticket));

            return "ticket-details";

        }

        return myTickets(model);

    }

    @GetMapping("/new-ticket")
    public String newTicket(Model model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        List<Project> projects = projectsListSetup(getUser());
        model.addAttribute("projects", projects);

        ArrayList<Object> arrayList = getTicketPST(model);

        model.addAttribute("priorities", arrayList.get(0));
        model.addAttribute("statuses", arrayList.get(1));
        model.addAttribute("types", arrayList.get(2));

        return "new-ticket";

    }

    @GetMapping("/ticket")
    public String getEditTicket(@RequestParam("ticketId") Integer ticketId, HttpServletRequest request, Model model) {
        Ticket ticket = ticketService.findById(ticketId);

        User user = getUser();

        // Check if user is eligible to edit project (Admin, Project Manager, Developer. Submitter cannot edit it after posting)
        if (((user.getDeveloper().contains(ticket) || ticket.getDeveloper() == null || user.getIdRole() == 2) && user.getProjects().contains(ticket.getProject())) || user.getIdRole() == 1 && ticket != null) {

            // If Ticket does not have assigned developer and if a developer is checking the ticket it will automatically assign to him.
            // Design idea: developer already sees the details before - entering edit mode means he accepts the responsibility
            // to fix/code the ticket
            if(user.getIdRole() == 3 && ticket.getDeveloper()== null){
                ticket.setDeveloper(user);
                TicketHistory ticketHistory = new TicketHistory(ticket,
                        "Developer",
                        "Not assigned",
                        user.getFirstName() + " " + user.getLastName(),
                        new Date());
                ticketService.saveHistory(ticketHistory);
                ticketService.save(ticket);
            } else {
                // list of users to assign the ticket are available for Project Manager and Admin (Submitter by default cannot enter edit mode)
                List<User> users = new ArrayList<>(projectService.findById(ticket.getProject().getId()).getUsers());
                List<User> developers = new ArrayList<>();
                users.forEach(user1 -> {
                    if(user1.getIdRole()==3){
                        developers.add(user1);
                    }
                });


                model.addAttribute("users", developers);
            }
            model.addAttribute("ticket", ticket);


            ArrayList<Object> arrayList = getTicketPST(model);

            model.addAttribute("priorities", arrayList.get(0));
            model.addAttribute("statuses", arrayList.get(1));
            model.addAttribute("types", arrayList.get(2));

            return "edit-ticket";
        }

        return myTickets(model);

    }

    @PostMapping("/ticket-save")
    public String saveTicket(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("project") int projectId,
                             @RequestParam(value = "user", required = false) Integer userId,
                             @RequestParam("priority") int priorityId,
                             @RequestParam("status") int statusId,
                             @RequestParam("type") int typeId) {

        Date date = new Date();

        Ticket ticket = new Ticket(title,
                description,
                projectService.findById(projectId),
                ticketService.findTypeById(typeId),
                ticketService.findPriorityById(priorityId),
                ticketService.findStatusById(statusId),
                getUser(),
                date);

        if (userId != null) {
            ticket.setDeveloper(userService.findUserById(userId));
        }

        ticketService.save(ticket);

        return "redirect:/ticket-details?ticketId=" + ticket.getId();
    }

    @PostMapping("/ticket-save-edit")
    public String saveEditTicket(@RequestParam("ticket") int ticketId,
                                 @RequestParam(value = "user", required = false) String userIdString,
                                 @RequestParam("priority") int priorityId,
                                 @RequestParam(value = "status", required = false) int statusId,
                                 @RequestParam(value = "type", required = false) int typeId) {

        Date date = new Date();
        boolean hasChanged = false;

        Ticket ticket = ticketService.findById(ticketId);
        int userId = userIdString==null ? 0 : Integer.parseInt(userIdString);
        int devId = ticket.getDeveloper() == null ? 0 : ticket.getDeveloper().getId();


        if (userId != devId) {
            User devUser = userId == 0 ? null : userService.findUserById(userId);

            String oldDevName = devId == 0 ?
                    "Not assigned" :
                    ticket.getDeveloper().getFirstName() + " " + ticket.getDeveloper().getLastName();

            String newDevName = userId == 0 ?
                    "Not assigned" :
                    devUser.getFirstName() + " " + devUser.getLastName();


            TicketHistory ticketHistory = new TicketHistory(ticket,
                    "Developer",
                    oldDevName,
                    newDevName,
                    date);
            ticketService.saveHistory(ticketHistory);
            ticket.setDeveloper(devUser);
            hasChanged = true;
        }

        if(ticket.getTicketPriority().getId() != priorityId){
            TicketPriority ticketPriority = ticketService.findPriorityById(priorityId);
            TicketHistory ticketHistory = new TicketHistory(ticket,
                    "Priority",
                    ticket.getTicketPriority().getName(),
                    ticketPriority.getName(),
                    date);
            ticketService.saveHistory(ticketHistory);
            ticket.setTicketPriority(ticketPriority);
            hasChanged = true;
        }
        if(ticket.getTicketStatus().getId() != statusId){
            TicketStatus ticketStatus = ticketService.findStatusById(statusId);
            TicketHistory ticketHistory = new TicketHistory(ticket,
                    "Status",
                    ticket.getTicketStatus().getName(),
                    ticketStatus.getName(),
                    date);
            ticketService.saveHistory(ticketHistory);
            ticket.setTicketStatus(ticketStatus);
            hasChanged = true;
        }
        if(ticket.getTicketType().getId() != typeId){
            TicketType ticketType = ticketService.findTypeById(typeId);
            TicketHistory ticketHistory = new TicketHistory(ticket,
                    "Type",
                    ticket.getTicketType().getName(),
                    ticketType.getName(),
                    date);
            ticket.setTicketType(ticketType);
            hasChanged = true;
        }

        if(hasChanged){
            ticket.setUpdated(date);
            ticketService.save(ticket);
        }

        return "redirect:/ticket-details?ticketId=" + ticketId;
    }


    @PostMapping("/ticket-comment-save")
    public String saveTicketComment(@RequestParam("ticketId") int ticketId,
                                    @RequestParam("message") String message) {

        Ticket ticket = ticketService.findById(ticketId);

        TicketComment ticketComment = new TicketComment(ticket, message, getUser(), new Date());

        ticketService.save(ticketComment);


        return "redirect:/ticket-details?ticketId=" + ticketId;
    }

    @PostMapping("/ticket-file-save")
    public String saveTicketFile(@RequestParam("ticketId") int ticketId,
                                 @RequestParam("uploadFile") MultipartFile file,
                                 @RequestParam("message") String message) {

        Ticket ticket = ticketService.findById(ticketId);
        TicketFiles ticketFiles = new TicketFiles(ticket, file.getOriginalFilename(), message, getUser(), new Date());
        storageService.store(file, ticketId);
        ticketService.saveFile(ticketFiles);

        return "redirect:/ticket-details?ticketId=" + ticketId;
    }

    @GetMapping("/image-resource/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResource(@PathVariable String filename, HttpServletResponse response) {

        Resource file = storageService.loadImage(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/download/{ticketId}/{fileId}")
    @ResponseBody
    public void getFile(@PathVariable String ticketId , @PathVariable String fileId, HttpServletResponse response, HttpServletRequest request, Model model) {

        User user = getUser();

        Ticket ticket;
        TicketFiles ticketFiles;
        // check if the ticketId and FileId are numbers
        try{
            ticket = ticketService.findById(Integer.parseInt(ticketId));
            ticketFiles = ticketService.findFileById(Integer.parseInt(fileId));
        } catch (Exception e){
            System.out.println("Error while parsing values: " + e.getMessage());
            throw new FileNotFoundException("There is no such file on server");

        }

        // check if ticket and file exist
        if(ticket != null && ticketFiles != null){
            // Check if the user has access to the ticket or is Admin (idRole = 1) or Project Manager (idRole = 2) for the ticket's project

            if(user.getDeveloper().contains(ticket) || user.getSubmitter().contains(ticket) || user.getIdRole() == 1 || (user.getIdRole() == 2 && user.getProjects().contains(ticket.getProject()))){
                Path file = Paths.get(storageProperties.getLocation() + "\\" + ticketId, ticketFiles.getFileName());

                if(Files.exists(file)){
                    response.addHeader("Content-Disposition", "attachment; filename="+ file.getFileName());
                    try{
                        Files.copy(file, response.getOutputStream());
                        response.getOutputStream().flush();
                        response.getOutputStream().close();
                    } catch (IOException e){
                        System.out.println("Error: " + e.getMessage());
                        throw new FileNotFoundException("There is no such file on server");
                    }

                }

            } else {
                // User has no access to the file - return 404 (not 500 server error)
                throw new FileNotFoundException("There is no such file on server");

            }

        } else {
            // ticket is null - return 404 (not 500 server error)
            throw new FileNotFoundException("There is no such file on server");

        }

    }


    // Error handling

    @GetMapping("/403")
    public String accessDenied(Model model) {

        // more processing that can be handled with 403 error

        return "403";

    }

    // == private functions ==

    // Returns current user
    private User getUser() {
        AuthUserDetails principal = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.findByEmail(principal.getUser().getEmail());
    }


    // returns valid list of projects depending on role (admin -> all, others -> assigned)
    private List<Project> projectsListSetup(User user) {
        List<Project> projects = projectService.findAll();

        // If: user is admin (idRole == 1) -> show all projects
        // Else: Show only projects the user (for example: Project Manager) is assigned to
        if (user.getIdRole() != 1) {
            if (!user.getProjects().isEmpty()) {
                projects = new ArrayList<>(user.getProjects());
                projects.sort(new ProjectSort());
            }
        }
        return projects;
    }

    // returns valid list of tickets depending on role (admin -> all, others -> assigned)
    private List<Ticket> ticketsListSetup(User user) {
        List<Ticket> tickets;


        // If: user is admin (idRole == 1) -> show all tickets from all projects
        // If: user is Project Manager (idRole == 2) -> show all tickets for assigned projects
        // If: user is Developer (idRole == 3) -> show assigned tickets to him and submitted by him to the projects he is assigned
        // user is Submitter (idRole == 4) -> can only see tickets submitted by him to the projects he is assigned
        switch (user.getIdRole()) {
            case 1:
                tickets = ticketService.findAll();
                tickets.sort(new TicketSort());
                break;
            case 2: // project manager can see all tickets from assigned projects
                tickets = ticketsForProjectManager(ticketService.findByProjectOrderById(new HashSet<>(user.getProjects())), user);
                break;
            case 3: // developer can both submit new tickets and resolve them
                LinkedHashSet<Ticket> hashSet = new LinkedHashSet<>(ticketService.findByDeveloper(user));
                tickets = ticketService.findBySubmitter(user);
                hashSet.addAll(tickets);
                tickets = new ArrayList<>(hashSet);
                tickets.sort(new TicketSort());
                break;
            case 4: // can only see his submitted tickets
                tickets = ticketService.findBySubmitter(user);
                break;
            default:
                tickets = new ArrayList<>();
                break;
        }

        return tickets;
    }

    // returns valid list of tickets depending on role for specific project (admin -> all, others -> assigned)

    private List<Ticket> ticketsPerProjectListSetup(User user, Project project) {
        List<Ticket> tickets;


        // If: user is admin (idRole == 1) -> show all tickets from all projects
        // If: user is Project Manager (idRole == 2) -> show all tickets for assigned projects
        // If: user is Developer (idRole == 3) -> show assigned tickets to him (also tickets without assigned developer) and submitted by him to the project he is assigned
        // user is Submitter (idRole == 4) -> can only see tickets submitted by him to the project he is assigned
        switch (user.getIdRole()) {
            case 1:
            case 2: // project manager can see all tickets from assigned project
                tickets = ticketsForProjectManager(ticketService.findByProject(project), user);

                break;
            case 3: // developer can both submit new tickets and resolve them
                LinkedHashSet<Ticket> hashSet = new LinkedHashSet<>(ticketService.findByDeveloper(user));
                hashSet.addAll(ticketService.findByDeveloper(null));
                tickets = ticketService.findBySubmitter(user);
                hashSet.addAll(tickets);

                LinkedHashSet<Ticket> tempHashSet = new LinkedHashSet<>();

                if(!hashSet.isEmpty()){
                    hashSet.forEach(ticket -> {
                        if(ticket.getProject().getId()==project.getId()){
                            tempHashSet.add(ticket);
                        }
                    });
                }

                tickets = new ArrayList<>(tempHashSet);
                tickets.sort(new TicketSort());
                break;
            case 4: // can only see his submitted tickets
                tickets = ticketService.findBySubmitter(user);
                List<Ticket> tempTickets = new ArrayList<>();
                tickets.forEach(ticket -> {
                    if(ticket.getProject().getId()==project.getId()){
                        tempTickets.add(ticket);
                    }
                });
                tickets = tempTickets;
                break;
            default:
                tickets = new ArrayList<>();
                break;
        }

        return tickets;
    }

    // getting Ticket Priorities, Statuses and Types to populate model on site
    private ArrayList<Object> getTicketPST(Model model) {
        ArrayList<Object> arrayList = new ArrayList<>();

        List<TicketPriority> priorities = ticketService.findAllTicketPriority();
        List<TicketStatus> statuses = ticketService.findAllTicketStatus();
        List<TicketType> types = ticketService.findAllTicketType();

        arrayList.add(priorities);
        arrayList.add(statuses);
        arrayList.add(types);

        return arrayList;
    }

    // lists tickets for project if the user is a Project Manager
    private List<Ticket> ticketsForProjectManager(List<Ticket> tickets, User user){
        if(!tickets.isEmpty()){
            List<Ticket> tempTickets = tickets;

            tickets.forEach(ticket -> {
                if (!user.getProjects().contains(ticket.getProject())) {
                    tempTickets.remove(ticket);
                }
            });
            tickets = tempTickets;
            tickets.sort(new TicketSort());
        } else {
            tickets = new ArrayList<>();
        }
        return tickets;
    }

}
