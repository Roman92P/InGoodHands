package pl.coderslab.charity.donation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;

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
        return "form";
    }

    @PostMapping("/confirm")
    public String createNewDonation(Donation donation){
        donationService.addDonation(donation);
        return "form-confirmation";
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
