package pl.coderslab.charity.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.user.CurrentUser;
import pl.coderslab.charity.user.UserService;

import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;

    public HomeController(InstitutionService institutionService, DonationService donationService, UserService userService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home(){
        return "redirect:/home";
    }

    @RequestMapping("/login")
    public String permitAllView(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login( CurrentUser currentUser, Model model) {
        User user = currentUser.getUser();
        if (user.isEnabled()) {
            return "redirect:/donations";
        }
        model.addAttribute("noActiveUser", "User is not activated. Check your email!");
        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message1", "User successfuly activated");
        } else {
            model.addAttribute("message2", "Activation code is no found");
        }
        return "login";
    }

    @RequestMapping("/home")
    public String homeAction(Model model){
        return "index";
    }

    @ModelAttribute("allInstitutions")
    public List<Institution> getAllInstitutions(){
        return institutionService.getAllInstitutions();
    }

    @ModelAttribute("allBags")
    public int allBags(){
        Optional<Integer> sumOfBags = donationService.getSumOfBags();
        return sumOfBags.orElse(0);
    }

    @ModelAttribute("allDonations")
    public int allDonations(){
        Optional<Integer> sumOfDonations = donationService.getSumOfDonations();
        return sumOfDonations.orElse(0);
    }
}
