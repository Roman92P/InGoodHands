package pl.coderslab.charity.passwordResetToken;

public interface PasswordResetTokenService {

    void createPasswordResetToken(PasswordResetToken token);

    PasswordResetToken findByToken(String token);
}
