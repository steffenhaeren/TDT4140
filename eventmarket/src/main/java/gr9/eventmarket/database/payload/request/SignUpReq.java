package gr9.eventmarket.database.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
public class SignUpReq {

    // can we set this up in a way that enables email or password?
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phoneNumber;

    private Set<String> roles;

    // TODO: do we really need setters? can this be replaced by the constructor?

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
