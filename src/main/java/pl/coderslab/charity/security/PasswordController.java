package pl.coderslab.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.model.PasswordResetToken;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.passwordResetToken.PasswordResetTokenService;
import pl.coderslab.charity.user.UserDTO;
import pl.coderslab.charity.user.UserService;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/recallPassword")
public class PasswordController {

    @Autowired
    private EmailService emailService;

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final SecurityService securityService;
    private final MessageSource messages;

    public PasswordController(UserService userService, PasswordResetTokenService passwordResetTokenService, SecurityService securityService, MessageSource messages) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.securityService = securityService;
        this.messages = messages;
    }

    @RequestMapping
    public String recallUserPassword(Model model){
        model.addAttribute("user",new UserDTO());
        return "userViews/recallPasswordView";
    }

    @PostMapping
    public String sendUrlToSetNewPassword(UserDTO userDTO, Model model){
        if(userDTO.getUserEmail()==null){
            model.addAttribute("message", "Brak takiego maila");
            return "recallPasswordView";
        }
        User user = null;
        Optional<User> byUserEmail = userService.findByUserEmail(userDTO.getUserEmail());
        if(!byUserEmail.isPresent()){
            model.addAttribute("message", "Brak takiego maila");
            return  "recallPasswordView";
        }
        user=byUserEmail.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetTokenService.createPasswordResetToken(passwordResetToken);
        user = byUserEmail.get();


        emailService.send(user.getUserEmail(),"Change password",
                "Dears user!\n " +
                        "To change your password use this url:\n " +
                        "http://localhost:8080/recallPassword/newPassword?token=" + token);
        return "redirect:/home";
    }

    @RequestMapping("/newPassword")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token){
        String result = securityService.validatePasswordResetToken(token);
        if(result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
            return "redirect:/login?lang="
                    + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }
}
