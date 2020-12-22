package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Institution;

import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }
    @RequestMapping
    public String permitAllView(){
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
