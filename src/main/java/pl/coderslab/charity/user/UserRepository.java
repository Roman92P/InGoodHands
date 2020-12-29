package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    User findByUserEmail(String userEmail);

    List<User> findAllByRolesEquals(Role userRole);
}
