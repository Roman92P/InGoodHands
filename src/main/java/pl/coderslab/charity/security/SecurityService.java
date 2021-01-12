package pl.coderslab.charity.security;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.passwordResetToken.PasswordResetToken;
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
        if (!isTokenFound(passToken)) {
            return "invalidToken";
        }
        if (isTokenExpired(passToken)) {
            return "expired";
        }
        return null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
