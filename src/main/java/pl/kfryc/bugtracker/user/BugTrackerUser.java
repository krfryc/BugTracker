package pl.kfryc.bugtracker.user;


import lombok.Getter;
import lombok.Setter;
import pl.kfryc.bugtracker.validation.FieldMatch;
import pl.kfryc.bugtracker.validation.UniqueEmail;
import pl.kfryc.bugtracker.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
@Getter
@Setter
public class BugTrackerUser {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message = "incorrect email")
    @ValidEmail
    @UniqueEmail
    @Size(min = 5, message = "is required")
    private String email;

    @NotNull(message = "is required")
    @Size(min = 6, max = 16, message = "Password must have from 6 to 16 characters")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 6, max = 16, message = "Password must have from 6 to 16 characters")
    private String matchingPassword;

    private int roleId;

    public BugTrackerUser() {
    }

    public BugTrackerUser(@NotNull(message = "is required") @Size(min = 1, message = "is required") String firstName, @NotNull(message = "is required") @Size(min = 1, message = "is required") String lastName, @NotNull(message = "is required") @Size(min = 5, message = "is required") String email, @NotNull(message = "is required") @Size(min = 6, message = "minimum 6 characters required") String password, @NotNull(message = "is required") @Size(min = 6, message = "minimum 6 characters required") String matchingPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

}
