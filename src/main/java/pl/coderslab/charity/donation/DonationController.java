package pl.coderslab.charity.donation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.user.CurrentUser;
import pl.coderslab.charity.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/donations")
public class DonationController {

    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final DonationService donationService;

    public DonationController(CategoryService categoryService, InstitutionService institutionService, DonationService donationService) {
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @RequestMapping
    public String getDonationForm(){
        return "donationViews/form";
    }

    @PostMapping("/confirm")
    public String createNewDonation(@ModelAttribute("newDonation") Donation donation, @AuthenticationPrincipal CurrentUser currentUser){
        donation.setUser(currentUser.getUser());
        donationService.addDonation(donation);
        return "donationViews/form-confirmation";
    }

    @ModelAttribute("newDonation")
    public Donation createDonation(){
        return new Donation();
    }

    @ModelAttribute("allCategories")
    public List<Category> getAllCategories(){
       return categoryService.getAllCategories();
    }

    @ModelAttribute("allInstitutions")
    public List<Institution>getAllInstitutions(){
        return institutionService.getAllInstitutions();
    }

}
