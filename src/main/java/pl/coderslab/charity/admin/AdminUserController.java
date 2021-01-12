package pl.coderslab.charity.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.user.Role;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.RoleRepository;
import pl.coderslab.charity.user.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminUserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("allUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @ModelAttribute("allRoles")
    public List<Role>allAvailableRoles(){
        return roleRepository.findAll();
    }

    @GetMapping("/details/{id}")
    public String showUserDetails(@PathVariable Long id, Model model){
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "adminViews/userDetails";
    }

    @PostMapping("/edit")
    public String editUser(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/admin/user/details/"+user.getId();
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "adminViews/adminAddUser";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result ){
        if(result.hasErrors()){
            return "adminViews/adminAddUser";
        }
        userService.createUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/addUser/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model, Principal principal){
        String name = principal.getName();
        User user = userService.getUserById(id).orElseThrow(EntityNotFoundException::new);
        String userName = user.getUserName();
        if(userName.equals(name)||userName.equals("Admin")){
            model.addAttribute("userDeleteMessage", "You cann't delete Admin or current logged user");
            model.addAttribute("user", user);
            return "adminViews/userDetails";
        }
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}
