package pl.coderslab.charity.user;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    private String password;

    private String userEmail;

    public UserDTO(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserDTO(String password, String userEmail){
        this.password = password;
        this.userEmail = userEmail;
    }

    public UserDTO() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
