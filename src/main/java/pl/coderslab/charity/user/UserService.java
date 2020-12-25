package pl.coderslab.charity.user;

import pl.coderslab.charity.model.User;

import java.util.List;

public interface UserService {
    User findByUserName(String name);
    void saveUser(User user);
    User findByUserEmail(String email);
    List<User> getAllUsers();
}
