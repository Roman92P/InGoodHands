package pl.coderslab.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.user.UserService;

import javax.validation.Valid;
import javax.validation.Validator;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    @Autowired
    Validator validator;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String getRegistrationView() {
        return "register";
    }

    @PostMapping
    public String addNewUser(@Valid @ModelAttribute("newUser")  User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/home";
    }

    @ModelAttribute("newUser")
    public User getNewUser() {
        return new User();
    }

}
