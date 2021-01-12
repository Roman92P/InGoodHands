package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findByUserEmail(String userEmail);
    List<User> findAllByRolesEquals(Role userRole);
    Optional<User> findByActivationCode(String code);

}
