package pl.kfryc.bugtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kfryc.bugtracker.service.BugTrackerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@Controller
public class BugTrackerController {

    private BugTrackerService bugTrackerService;

    @Autowired
    public BugTrackerController(BugTrackerService bugTrackerService) {
        this.bugTrackerService = bugTrackerService;
    }

    // main page after login
    @GetMapping("/")
    public String indexPage(Model model){
        return bugTrackerService.indexPage(model);
    }

    @GetMapping("/manage-roles")
    public String manageRoles(Model model) {
        return bugTrackerService.manageRoles(model);
    }

    @GetMapping("/user")
    public String userProfile(Model model) {

        return bugTrackerService.userProfile(model);
    }

    @PostMapping("/change-profile-pic")
    public String changeProfilePic(@RequestParam("profilePic") MultipartFile file) {

        return bugTrackerService.changeProfilePic(file);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("password") String password) {

        return bugTrackerService.changePassword(oldPassword, password);
    }


    @PostMapping("/manage-roles-update")
    public String updateRoles(@RequestParam("users") HashSet<Integer> usersId,
                              @RequestParam("role") int roleId) {

        return bugTrackerService.updateRoles(usersId,roleId);
    }


    @GetMapping("/manage-project-users")
    public String manageProjectUsers(Model model) {
        return bugTrackerService.manageProjectUsers(model);
    }


    @PostMapping("/manage-project-users-update")
    public String updateProjectUsers(@RequestParam("users") HashSet<Integer> usersId,
                                     @RequestParam("project") int projectId) {

        return bugTrackerService.updateProjectUsers(usersId, projectId);
    }

    @PostMapping("/manage-project-users-delete")
    public String deleteProjectUsers(@RequestParam("users") HashSet<Integer> usersId,
                                     @RequestParam("project") int projectId) {

        return bugTrackerService.deleteProjectUsers(usersId, projectId);
    }


    // List all projects

    @GetMapping("/projects")
    public String myProjects(Model model) {

        return bugTrackerService.myProjects(model);
    }

    @GetMapping("/project-details")
    public String projectDetails(@RequestParam("projectId") int projectId,
                                 Model model) {

        return bugTrackerService.projectDetails(projectId, model);
    }


    // GetMapping to edit single project

    @GetMapping("/project")
    public String getEditProject(@RequestParam("projectId") int projectId, HttpServletRequest request, Model model) {

        return bugTrackerService.getEditProject(projectId, request, model);
    }

    @GetMapping("/new-project")
    public String newProject(Model model) {

        return bugTrackerService.newProject(model);
    }

    @PostMapping("/project-save")
    public String saveProject(@RequestParam("users") HashSet<Integer> usersId,
                              @RequestParam("name") String projectName,
                              @RequestParam("description") String description) {

        return bugTrackerService.saveProject(usersId, projectName, description);
    }

    @PostMapping("/project-save-edit")
    public String saveEditProject(@RequestParam("id") int projectId,
                                  @RequestParam("name") String projectName,
                                  @RequestParam("description") String description) {

        return bugTrackerService.saveEditProject(projectId, projectName, description);
    }

    // My Tickets

    @GetMapping("/tickets")
    public String myTickets(Model model) {

        return bugTrackerService.myTickets(model);

    }

    @GetMapping("/ticket-details")
    public String ticketDetails(@RequestParam("ticketId") int ticketId,
                                Model model) {

        return bugTrackerService.ticketDetails(ticketId, model);
    }

    @GetMapping("/new-ticket")
    public String newTicket(Model model) {

        return bugTrackerService.newTicket(model);
    }

    @GetMapping("/ticket")
    public String getEditTicket(@RequestParam("ticketId") Integer ticketId, HttpServletRequest request, Model model) {

        return bugTrackerService.getEditTicket(ticketId, request, model);
    }

    @PostMapping("/ticket-save")
    public String saveTicket(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("project") int projectId,
                             @RequestParam(value = "user", required = false) Integer userId,
                             @RequestParam("priority") int priorityId,
                             @RequestParam("status") int statusId,
                             @RequestParam("type") int typeId) {

        return bugTrackerService.saveTicket(title, description, projectId, userId, priorityId, statusId, typeId);
    }

    @PostMapping("/ticket-save-edit")
    public String saveEditTicket(@RequestParam("ticket") int ticketId,
                                 @RequestParam(value = "user", required = false) String userIdString,
                                 @RequestParam("priority") int priorityId,
                                 @RequestParam(value = "status", required = false) int statusId,
                                 @RequestParam(value = "type", required = false) int typeId) {

        return bugTrackerService.saveEditTicket(ticketId, userIdString, priorityId, statusId, typeId);
    }


    @PostMapping("/ticket-comment-save")
    public String saveTicketComment(@RequestParam("ticketId") int ticketId,
                                    @RequestParam("message") String message) {

        return  bugTrackerService.saveTicketComment(ticketId, message);
    }

    @PostMapping("/ticket-file-save")
    public String saveTicketFile(@RequestParam("ticketId") int ticketId,
                                 @RequestParam("uploadFile") MultipartFile file,
                                 @RequestParam("message") String message) {

        return bugTrackerService.saveTicketFile(ticketId, file, message);
    }

    @GetMapping("/image-resource/{filename}")
    @ResponseBody
    public ResponseEntity<byte[]> getImageAsResource(@PathVariable String filename, HttpServletResponse response) throws IOException {

        return bugTrackerService.getImageAsResource(filename, response);
    }

    @GetMapping("/download/{ticketId}/{fileId}")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable String ticketId , @PathVariable String fileId, HttpServletResponse response, HttpServletRequest request, Model model) throws IOException{

        return bugTrackerService.getFile(ticketId, fileId, response, request, model);

    }


    // Error handling

    @GetMapping("/403")
    public String accessDenied(Model model) {

        // more processing that can be handled with 403 error

        return "403";

    }

}
