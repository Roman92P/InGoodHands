package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.user.RoleRepository;
import pl.coderslab.charity.user.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final RoleRepository roleRepository;

    @Autowired
    Validator validator;

    public AdminController(UserService userService, InstitutionService institutionService, DonationService donationService, RoleRepository roleRepository) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping
    public String getAdminPanel(){
        return "admin";
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


    @RequestMapping("/institution/remove/{id}")
    public String deleteInstitution(@PathVariable Long id){
        institutionService.removeInstitution(institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new));
        return "redirect:/admin";
    }

    @RequestMapping("/institution/edit/{id}")
    public String editInstitution(@PathVariable Long id, Model model){
        Institution institution = institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("institution", institution);
        return"editInstitution";
    }

    @PostMapping("/institution/edit")
    public String editIstitution(Institution institution){
        institutionService.updateteInstitution(institution);
        return "redirect:/admin";
    }

    @RequestMapping("/user/details/{id}")
    public String showUserDetails(@PathVariable Long id, Model model){
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "userDetails";
    }

    @PostMapping("/user/edit")
    public String editUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/admin/user/details/"+user.getId();
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @ModelAttribute("allRoles")
    public List<Role>allAvailableRoles(){
        return roleRepository.findAll();
    }

    @RequestMapping("/donation/details/{id}")
    public String getDetailsOfDonation(@PathVariable Long id, Model model){
        model.addAttribute("donation", donationService.getDonation(id).orElseThrow(EntityNotFoundException::new));
        return "donationDetails";
    }

    @RequestMapping("/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "adminAddUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result ){
        if(result.hasErrors()){
            return "adminAddUser";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/addInstitution")
    public String addInstitution(Model model){
        model.addAttribute("institution", new Institution());
        return"adminAddInstitution";
    }

    @PostMapping("/addInstitution")
    public String addInstitution(@Valid Institution institution, BindingResult result){
        if(result.hasErrors()){
            return "adminAddInstitution";
        }
        institutionService.addInstitution(institution);
        return "redirect:/admin";
    }

    @RequestMapping("/addUser/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model, Principal principal){
        String name = principal.getName();
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        String userName = user.getUserName();
        if(userName.equals(name)||userName.equals("Admin")){
            model.addAttribute("userDeleteMessage", "You cann't delete Admin or current logged user");
            model.addAttribute("user", user);
            return "userDetails";
        }
        userService.deleteUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/adminList")
    public String getAdminList(Model model){
        model.addAttribute("admins", userService.findAllAdmins());
        model.addAttribute("user", new User());
        return "adminList";
    }

    @RequestMapping("/edit/{id}")
    public String editAdminUser(@PathVariable Long id, Model model){
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "editAdminUser";
    }

    @PostMapping("/edit")
    public String editAdminUser(@Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("admins", userService.findAllAdmins());
            return "adminList";
        }
        userService.updateUser(user);
        return "redirect:/admin/adminList";
    }

    @RequestMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, Model model, Principal principal){
        String name = principal.getName();
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        String userName = user.getUserName();
        if(userName.equals(name)||userName.equals("Admin")){
            model.addAttribute("adminDeleteMessage", "You cann't delete Admin or current logged user");
            model.addAttribute("admins", userService.findAllAdmins());
            return "adminList";
        }
        userService.deleteUser(user);
        return "redirect:/admin/adminList";
    }

    @RequestMapping("/addNewAdmin")
    public String adminAddNewAdmin(Model model){
        User user = new User();
        model.addAttribute("newAdmin", user);
        return "adminAddNewAdmin";
    }

    @PostMapping("/addNewAdmin")
    public String adminAddNewAdmin(@Valid @ModelAttribute("newAdmin") User user, BindingResult result){
        if(result.hasErrors()){
            return "adminAddNewAdmin";
        }
        Set<Role> userRoles = new HashSet<>();
        Role role_admin = roleRepository.findByName("ROLE_ADMIN");
        userRoles.add(role_admin);
        user.setRoles(userRoles);
        userService.saveUser(user);
        return "redirect:/admin/adminList";
    }
}
