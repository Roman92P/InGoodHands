package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
