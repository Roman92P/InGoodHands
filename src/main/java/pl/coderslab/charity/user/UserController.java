package pl.coderslab.charity.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final SpringDataUserDetailsService springDataUserDetailsService;
    private final DonationService donationService;

    public UserController(UserService userService, SpringDataUserDetailsService springDataUserDetailsService, DonationService donationService) {
        this.userService = userService;
        this.springDataUserDetailsService = springDataUserDetailsService;
        this.donationService = donationService;
    }

    @RequestMapping("/profile")
    public String getUserProfile(Principal principal, Model model, @AuthenticationPrincipal CurrentUser currentUser){
        String name = principal.getName();
        User byUserName = userService.findByUserName(name).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", byUserName);
        return"userProfile";
    }

    @PostMapping("/profile")
    public String editUserProfile(@Valid User user, BindingResult result, Model model, @AuthenticationPrincipal CurrentUser currentUser){
        if(result.hasErrors()){
            User byUserName = userService.findByUserName(user.getUserName()).orElseThrow(EntityNotFoundException::new);
            model.addAttribute("user", byUserName);
            return"userProfile";
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.updateUser(user);
        return "redirect:/user/profile";
    }

    @RequestMapping("/donations")
    public String viewAllUserDonations(Model model, @AuthenticationPrincipal CurrentUser user){
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.usersDonations(id);
        model.addAttribute("allDonations", donations);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentT = LocalTime.now();
        String timeNow = currentT.format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime parse = LocalTime.parse(timeNow);
        model.addAttribute("dateNow", currentDate);
        model.addAttribute("timeNow", parse);
        return "userDonations";
    }


}
