package pl.coderslab.charity.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.model.Donation;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin/donation")
public class AdminDonationController {

    private final DonationService donationService;

    public AdminDonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @ModelAttribute("allDonations")
    public List<Donation> getAllDonations(){
        return  donationService.getAllDonations();
    }

    @RequestMapping("/details/{id}")
    public String getDetailsOfDonation(@PathVariable Long id, Model model){
        model.addAttribute("donation", donationService.getDonation(id).orElseThrow(EntityNotFoundException::new));
        return "adminViews/donationDetails";
    }

}
