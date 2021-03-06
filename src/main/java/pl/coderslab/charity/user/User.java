package pl.coderslab.charity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.donation.Donation;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 15, message = "Imię użytkownika nie może być puste")
    @Column(name = "user_name", unique = true)
    private String userName;

    @Size(min = 2, max = 15, message = "Nazwisko użytkownika nie może być puste")
    @Column(name = "user_lastname")
    private String userLastname;

    @NotEmpty(message = "Mail nie może być pusty")
    @Email
    @Column(name = "user_email", unique = true)
    private String userEmail;

    @NotEmpty (message = "hasło nie może być puste")
    @Size(min = 8, message = "Hasło powinno zawierać min 8 znaków")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{8,}$", message = "Hasło powinno zawierać przynajmniej:\n-jeden mały znak\n-jeden duży znak\n-jeden znak " +
            "specjalny\n-jedną liczbę")
    @Column(name="user_password")
    private String password;

    @Column(name = "isEnabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Donation>donations;

    public User() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    private String activationCode;

}
