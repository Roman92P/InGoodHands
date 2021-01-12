package pl.coderslab.charity.institution;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Institution;

import java.util.List;
import java.util.Optional;

@Service
public class JpaInstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    public JpaInstitutionServiceImpl(InstitutionRepository institutionRepository){
        this.institutionRepository = institutionRepository;
    }

    @Override
    public Optional<Institution> getInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    @Override
    public void addInstitution(Institution institution) {
        institutionRepository.save(institution);
    }

    @Override
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    @Override
    public void updateInstitution(Institution institution) {
        institutionRepository.save(institution);
    }

    @Override
    public void removeInstitution(Institution institution) {
        institutionRepository.delete(institution);
    }
}
