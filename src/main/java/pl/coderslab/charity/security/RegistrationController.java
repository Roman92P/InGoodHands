package pl.coderslab.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.UserService;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Locale;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final MessageSource messageSource;
    private final UserService userService;
    @Autowired
    Validator validator;

    public RegistrationController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @RequestMapping
    public String getRegistrationView() {
        return "register";
    }

    @PostMapping
    public String addNewUser(@Valid @ModelAttribute("newUser")  User user, BindingResult result,
                             Model model, Locale locale) {
        if (result.hasErrors()) {
            return "register";
        }
        if(userService.checkIfUserNameExist(user.getUserName())){
            String message = messageSource.getMessage("registration.userName.exist", null, locale);
            return "redirect:/register?lang="+locale.getLanguage()+"&messageName="+message;
        }
        userService.createUser(user);
        model.addAttribute("successEmail", "Email with activation has been sent! Check your email.");
        return "login";
    }

    @ModelAttribute("newUser")
    public User getNewUser() {
        return new User();
    }

}
