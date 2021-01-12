package pl.coderslab.charity.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.Institution;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/institution")
public class AdminInstitutionController {

    private final InstitutionService institutionService;

    public AdminInstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/remove/{id}")
    public String deleteInstitution(@PathVariable Long id){
        institutionService.removeInstitution(institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new));
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editInstitution(@PathVariable Long id, Model model){
        Institution institution = institutionService.getInstitution(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("institution", institution);
        return"adminViews/editInstitution";
    }

    @PostMapping("/edit")
    public String editIstitution(Institution institution){
        institutionService.updateInstitution(institution);
        return "redirect:/admin";
    }
    @ModelAttribute("allInstitutions")
    public List<Institution> getAllInstitutions(){
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/addInstitution")
    public String addInstitution(Model model){
        model.addAttribute("institution", new Institution());
        return"adminViews/adminAddInstitution";
    }

    @PostMapping("/addInstitution")
    public String addInstitution(@Valid Institution institution, BindingResult result){
        if(result.hasErrors()){
            return "adminViews/adminAddInstitution";
        }
        institutionService.addInstitution(institution);
        return "redirect:/admin";
    }
}
