package pl.coderslab.charity.institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionService {
    Optional<Institution> getInstitution(Long id);
    void addInstitution(Institution institution);
    List<Institution> getAllInstitutions();
    void updateInstitution(Institution institution);
    void removeInstitution(Institution institution);
}
