package pl.coderslab.charity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @Column(name = "user_id")
    private int id;

    @Size(min = 2, max = 15, message = "Imię użytkownika nie może być pusta")
    @Column(name = "user_name")
    private String userName;

    @Size(min = 2, max = 15, message = "Nzwisko użytkownika nie może być puste")
    @Column(name = "user_lastname")
    private String userLastname;

    @Email
    @Column(name = "user_email")
    private String userEmail;

    @NotEmpty (message = "hasło nie może być puste")
    @Column(name="user_password")
    private String password;

    @Column(name = "isEnabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles=new HashSet<>();

    public User() {
    }

    public boolean isEnabled() {
        return enabled;
    }

}
