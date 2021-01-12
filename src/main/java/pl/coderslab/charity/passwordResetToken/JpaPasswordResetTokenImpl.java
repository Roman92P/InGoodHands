package pl.coderslab.charity.passwordResetToken;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.PasswordResetToken;

@Service
public class JpaPasswordResetTokenImpl implements PasswordResetTokenService{

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public JpaPasswordResetTokenImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void createPasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

}
