package pl.coderslab.charity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.User;

import javax.persistence.EntityNotFoundException;

@Service
public class UserDTOService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTOService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String validateCurrentUserPassword(UserDTO userDTO){
        return !isCurrentPasswordCorrect(userDTO) ? "wrongCurrentPassword"
                : null;
    }

    public String validatePasswordMatch(UserDTO user){
        return !isPasswordsMatches(user) ?  "matchFault"
                : null;
    }

    private boolean isPasswordsMatches(UserDTO userDTO){
        return userDTO.getPassword().equals(userDTO.getPasswordConfirmation());
    }

    private boolean isCurrentPasswordCorrect(UserDTO userDTO){
        User byUserEmail = userService.findByUserEmail(userDTO.getUserEmail()).orElseThrow(EntityNotFoundException::new);
        String encode = passwordEncoder.encode(userDTO.getCurrentUserPassword());
        return passwordEncoder.matches(userDTO.getCurrentUserPassword(), byUserEmail.getPassword());
    }

}
