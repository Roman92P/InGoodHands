package pl.coderslab.charity.user;

import pl.coderslab.charity.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String name);
    boolean saveUser(User user);
    User findByUserEmail(String email);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void updateUser(User user);
    void deleteUser(User user);
    List<User> findAllAdmins();
    boolean activateUser(String code);
    Optional<User> findByActivationCode(String code);
}
