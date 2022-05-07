package gr9.eventmarket.database.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr9.eventmarket.database.model.ads.Advert;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String userID;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 321)
    @Email
    private String email;

    @NotBlank
    @Size(max = 32)
    private String phoneNumber;

    // Has to be ignored to avoid transmitting by accident
    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String phoneNumber, String password) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("That is not a valid username");
        }
        if (!isValidEmailAddress(email)) {
            throw new IllegalArgumentException("That is not a valid email address");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("That is not a valid password");
        }
        if (!isValidNumber(phoneNumber)) {
            throw new IllegalArgumentException("That is not a valid phone number");
        }

        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getId() {
        return userID;
    }

    public void setId(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Field validation {{{
    private boolean isValidUsername(String username) {
        // Criteria: At least three characters. can contain upper and lower case
        // letters, digist, points, dashes or underscores.
        return Pattern.compile("[a-zA-Z0-9\\._\\-]{3,}").matcher(username).matches();
    }

    private boolean isValidEmailAddress(String email) {
        // Criteria: Must match a valid email format.
        return Pattern.compile("^(.{1,64})@(.{1,255})$").matcher(email).matches();
    }

    private boolean isValidNumber(String phoneNumber) {
        // Phone numbers are optional, so we don't make it mandatory.
        if (phoneNumber == null) return true;
        // Criteria: Must only contain eight digits.
        return Pattern.compile("^[0-9]*$").matcher(phoneNumber).matches() && (phoneNumber.length() == 8);
    }

    private boolean isValidPassword(String password) {
        // Criteria: Must contain mix of upper and lower case letters, be at least 8
        // characters and contain at least
        // one digit.
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$").matcher(password).matches();
    }
    // }}}

    public boolean isMod() {
        for (Role role : roles) {
            if (role.getName() == ERole.ROLE_MOD) {
                return true;
            }
        }
        return false;
    }

    public void flagAdvert(Advert advert) {
        if (isMod()) {
            advert.setAdvertAsFlagged();
        }
    }

    public void giveModeratorRights(User userToGetRights) {
        if (isMod()) {
            userToGetRights.setRoles(new HashSet<>(Arrays.asList(new Role(ERole.ROLE_MOD))));
        }
    }
}
