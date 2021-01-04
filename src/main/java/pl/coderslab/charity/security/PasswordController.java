package pl.coderslab.charity.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.user.UserDTO;

@Controller
@RequestMapping("/recallPassword")
public class PasswordController {

    @RequestMapping
    public String recallUserPassword(Model model){
        model.addAttribute("userPassword",new UserDTO());
        return "userViews/recallPasswordView";
    }
}
