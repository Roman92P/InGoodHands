package pl.coderslab.charity.passwordResetToken;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}
