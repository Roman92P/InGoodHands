package pl.coderslab.charity.passwordResetToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.user.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class PasswordResetToken {

    private static final int EXPIRATION = 60000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;


    @PrePersist
    private void setExpiration(){
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        this.expiryDate = new Date(timeInMillis+(5 * EXPIRATION));
    }

    public PasswordResetToken() {

    }
}
