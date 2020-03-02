package pl.kfryc.bugtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kfryc.bugtracker.entity.User;
import pl.kfryc.bugtracker.service.UserService;
import pl.kfryc.bugtracker.user.BugTrackerUser;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @InitBinder
    public void initBindier(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("user", new BugTrackerUser());

        return "register";
    }

    @GetMapping(path = "/save")
    public String checkUser(Model model){
        model.addAttribute("user", new BugTrackerUser());

        return "register";
    }

    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") BugTrackerUser user,
            BindingResult bindingResult,
            Model model){


        // check if email already exists
        User regUser = userService.findByEmail(user.getEmail());
        model.addAttribute("user", user);
        if(regUser!=null){
            //model.addAttribute("user", new BugTrackerUser());
            model.addAttribute("registrationError", "Email already exists");

            return "register";
        }

        if(bindingResult.hasErrors()){
            return "register";
        }

        userService.save(user);

        return "redirect:/login";
    }
}
