package pl.coderslab.charity.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.user.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String getRegistrationView(Model model){
        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping
    public String addNewUser(User user){
        userService.saveUser(user);
        return "redirect:/";
    }

}
