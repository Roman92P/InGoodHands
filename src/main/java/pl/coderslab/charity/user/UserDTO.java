package pl.coderslab.charity.user;

public class UserDTO {

    private String password;

    public UserDTO(String password) {
        this.password = password;
    }

    public UserDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
