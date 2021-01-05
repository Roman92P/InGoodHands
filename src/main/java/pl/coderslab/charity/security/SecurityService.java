package pl.coderslab.charity.security;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.PasswordResetToken;
import pl.coderslab.charity.passwordResetToken.PasswordResetTokenService;

import java.util.Calendar;

@Service
public class SecurityService {

    private final PasswordResetTokenService passwordResetTokenService;

    public SecurityService(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }


    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenService.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
