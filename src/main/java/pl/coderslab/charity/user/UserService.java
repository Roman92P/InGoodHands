package pl.coderslab.charity.user;

import pl.coderslab.charity.model.User;

public interface UserService {
    User findByUserName(String name);
    void saveUser(User user);
}
