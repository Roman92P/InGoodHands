package pl.coderslab.charity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.coderslab.charity.email.EmailService;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class JpaUserServiceImpl implements UserService {

    @Autowired
    private EmailService emailService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public JpaUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public boolean saveUser(User user) {

        Optional<User> byUserName = userRepository.findByUserName(user.getUserName());
        if(byUserName.isPresent()){
            return false;
        }

        user.setActivationCode(UUID.randomUUID().toString());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        if (user.getRoles().size()==0) {
            Role userRole = roleRepository.findByName("ROLE_USER");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        }
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getUserEmail())){
            String message = String.format("Hello %s! Welcome to charity app. Please activate your account" +
                    " throught this link:http://localhost:8080/activate/%s", user.getUserName(), user.getActivationCode());
            emailService.send(user.getUserEmail(), "Activation code", message);
        }
        return true;
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> user =  userRepository.findByActivationCode(code);
        if(!user.isPresent()){
            return false;
        }
        User user1 = user.get();
        user1.setActivationCode(null);
        user1.setEnabled(true);
        userRepository.save(user1);
        return true;
    }

    @Override
    public Optional<User> findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }

    @Override
    public void addRole(Long id, String role_change_password_privilege) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            Set<Role> roles = user.getRoles();
            roles.add(roleRepository.findByName(role_change_password_privilege));
            userRepository.save(user);
        }
    }

    @Override
    public void setNewPossword(User user, String password) {
        User userToUpdate = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        userToUpdate.setPassword(passwordEncoder.encode(password));
        userRepository.save(userToUpdate);
    }


    @Override
    public Optional<User> findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user) {
        Optional<User> byId = userRepository.findById(user.getId());
        if(byId.isPresent()){
            User user1 = byId.get();
            String password = user1.getPassword();
            if(!password.equals(user.getPassword())){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllAdmins() {
       return userRepository.findAllByRolesEquals(roleRepository.findByName("ROLE_ADMIN"));
    }
}
