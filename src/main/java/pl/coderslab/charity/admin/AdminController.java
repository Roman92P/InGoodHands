package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.donation.Donation;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.user.Role;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.RoleRepository;
import pl.coderslab.charity.user.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final RoleRepository roleRepository;
    private final MessageSource messages;

    @Autowired
    Validator validator;

    public AdminController(UserService userService, InstitutionService institutionService, DonationService donationService, RoleRepository roleRepository, @Qualifier("messageSource") MessageSource messages) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.roleRepository = roleRepository;
        this.messages = messages;
    }
    @RequestMapping
    public String getAdminPanel(){
        return "adminViews/admin";
    }

    @ModelAttribute("allUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @ModelAttribute("allInstitutions")
    public List<Institution> getAllInstitutions(){
        return institutionService.getAllInstitutions();
    }

    @ModelAttribute("allDonations")
    public List<Donation> getAllDonations(){
        return  donationService.getAllDonations();
    }

    @ModelAttribute("allRoles")
    public List<Role>allAvailableRoles(){
        return roleRepository.findAll();
    }

    @GetMapping("/adminList")
    public String getAdminList(Model model){
        model.addAttribute("admins", userService.findAllAdmins());
        model.addAttribute("user", new User());
        return "adminViews/adminList";
    }

    @GetMapping("/edit/{id}")
    public String editAdminUser(@PathVariable Long id, Model model){
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "adminViews/editAdminUser";
    }

    @PostMapping("/edit")
    public String editAdminUser(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("admins", userService.findAllAdmins());
            return "adminViews/adminList";
        }
        userService.updateUser(user);
        return "redirect:/admin/adminList";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, Model model, Principal principal){
        String name = principal.getName();
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        String userName = user.getUserName();
        if(userName.equals(name)||userName.equals("Admin")){
            model.addAttribute("adminDeleteMessage", "You cann't delete Admin or current logged user");
            model.addAttribute("admins", userService.findAllAdmins());
            return "adminViews/adminList";
        }
        userService.deleteUser(user);
        return "redirect:/admin/adminList";
    }

    @GetMapping("/addNewAdmin")
    public String adminAddNewAdmin(Model model){
        User user = new User();
        model.addAttribute("newAdmin", user);
        return "adminViews/adminAddNewAdmin";
    }

    @PostMapping("/addNewAdmin")
    public String adminAddNewAdmin(@Valid @ModelAttribute("newAdmin") User user, BindingResult result, Locale locale, Model model){
        if(result.hasErrors()){
            return "adminViews/adminAddNewAdmin";
        }
        if(userService.findByUserEmail(user.getUserEmail()).isPresent()){
            String message = messages.getMessage("email.ableToRegister.not", null, locale);
            User user1 = new User();
            model.addAttribute("newAdmin", user1);
            return "redirect:/admin/addNewAdmin?lang="+locale.getCountry()+"&message="+message;
        }
        Set<Role> userRoles = new HashSet<>();
        Role role_admin = roleRepository.findByName("ROLE_ADMIN");
        userRoles.add(role_admin);
        user.setRoles(userRoles);
        userService.createUser(user);
        return "redirect:/admin/adminList";
    }
}
