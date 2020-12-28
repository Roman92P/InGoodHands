package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findByUserEmail(String userEmail);

    List<User> findAllByRolesEquals(Role userRole);
}
