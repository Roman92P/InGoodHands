package pl.coderslab.charity.passwordResetToken;

import pl.coderslab.charity.model.PasswordResetToken;

public interface PasswordResetTokenService {

    void createPasswordResetToken(PasswordResetToken token);

    PasswordResetToken findByToken(String token);
    void updatePasswordResetToken(PasswordResetToken passwordResetToken);
}
