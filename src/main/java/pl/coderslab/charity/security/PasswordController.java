package pl.coderslab.charity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.model.PasswordResetToken;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.passwordResetToken.PasswordResetTokenService;
import pl.coderslab.charity.user.SpringDataUserDetailsService;
import pl.coderslab.charity.user.UserDTO;
import pl.coderslab.charity.user.UserPasswordValidator;
import pl.coderslab.charity.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/recallPassword")
public class PasswordController {

    Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private EmailService emailService;

    private final SpringDataUserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final SecurityService securityService;
    private final MessageSource messages;
    private final UserPasswordValidator userPasswordValidator;

    public PasswordController(SpringDataUserDetailsService userDetailsService, UserService userService, PasswordResetTokenService passwordResetTokenService, SecurityService securityService, @Qualifier("messageSource") MessageSource messages, UserPasswordValidator userPasswordValidator) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.securityService = securityService;
        this.messages = messages;
        this.userPasswordValidator = userPasswordValidator;
    }

    @ModelAttribute("user")
    public UserDTO getNewUserDTO(){
        return new UserDTO();
    }

    @RequestMapping
    public String recallUserPassword(Model model) {
        model.addAttribute("user", new UserDTO());
        return "userViews/recallPasswordView";
    }

    @PostMapping
    public String sendUrlToSetNewPassword(UserDTO userDTO, Model model) {
        if (userDTO.getUserEmail() == null) {
            model.addAttribute("mailAbsent", "Pole mail nie może być puste");
            return "userViews/recallPasswordView";
        }
        User user = null;
        Optional<User> byUserEmail = userService.findByUserEmail(userDTO.getUserEmail());
        if (!byUserEmail.isPresent()) {
            model.addAttribute("message", "Brak takiego maila w bazie");
            return "userViews/recallPasswordView";
        }
        user = byUserEmail.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetTokenService.createPasswordResetToken(passwordResetToken);
        user = byUserEmail.get();


        emailService.send(user.getUserEmail(), "Change password",
                "Dears user!\n " +
                        "To change your password use this url:\n " +
                        "http://localhost:8080/recallPassword/newPassword?token=" + token + "\n" +
                        "Token is valid during 5 minutes!");
        return "redirect:/home";
    }

    @RequestMapping("/newPassword")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token, Authentication authentication) {
        String result = securityService.validatePasswordResetToken(token);
        if (result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
            return "redirect:/login?lang="
                    + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            PasswordResetToken byToken = passwordResetTokenService.findByToken(token);
            Collection<? extends GrantedAuthority> authorities = userDetailsService.loadUserByUsername(byToken.getUser().getUserName()).getAuthorities();
            Collection<? extends GrantedAuthority> authorities1 = Collections.singleton(new SimpleGrantedAuthority("ROLE_CHANGE_PASSWORD_PRIVILEGE"));
            authentication = new UsernamePasswordAuthenticationToken(byToken.getUser().getUserName(), null,
                    authorities1);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/recallPassword/updatePassword?lang=" + locale.getLanguage()+"&token="+token;
        }
    }

    //zmienić na GET
    @RequestMapping("/updatePassword")
    public String showPasswordChangeView(@RequestParam("token") String token, Principal principal, Model model) {
        User user= userService.findByUserName(principal.getName()).orElseThrow(EntityNotFoundException::new);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail(user.getUserEmail());
        model.addAttribute("user", userDTO);
        model.addAttribute("token",token);
        return "/userViews/updatePassword";
    }

    @Secured("{CHANGE_PASSWORD_PRIVILEGE}")
    @PostMapping("/updatePassword/{token}")
    public String updateUserPassword(@ModelAttribute("user") UserDTO userDTO, @PathVariable String token ,
                                     Model model, Locale locale, Authentication authentication){

        String s = userPasswordValidator.validatePasswordMatch(userDTO);
        if(s != null){
            String message = messages.getMessage("password.matches"+s,null, locale);
            return "redirect:/recallPassword/updatePassword?lang="+locale.getLanguage()+"&token="+token+
                    "&passwordMessage="+message;

        }
        if(userDTO.getPassword()==null){
            return "redirect:/recallPassword/newPassword?lang=" + locale.getLanguage()+"&token="+token;
        }
        User user = null;
        try{
            user = passwordResetTokenService.findByToken(token).getUser();
        }catch (NullPointerException e){
            model.addAttribute("emptyToken", "Nie ma takiego tokenu");
            return "login";
        }
        userService.setNewPassword(user,userDTO.getPassword());
        authentication.setAuthenticated(false);
        model.addAttribute("successPasswordUpdate", "Hasło zostało pomyślnie zmienione");
        return "login";
    }

}
