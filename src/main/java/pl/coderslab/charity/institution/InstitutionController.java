package pl.coderslab.charity.institution;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Institution;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @RequestMapping("/remove/{id}")
    public String deleteInstitution(@PathVariable Long id){
        institutionService.removeInstitution(institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new));
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public String editInstitution(@PathVariable Long id, Model model){
        Institution institution = institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("institution", institution);
        return"editInstitution";
    }

    @PostMapping("/edit")
    public String editIstitution(Institution institution){
        institutionService.updateteInstitution(institution);
        return "redirect:/admin";
    }
}
