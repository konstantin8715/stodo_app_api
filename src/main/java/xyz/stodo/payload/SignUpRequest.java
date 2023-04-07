package xyz.stodo.payload;

import lombok.Data;
import xyz.stodo.validation.annotation.PasswordMatches;
import xyz.stodo.validation.annotation.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {
    @Email(message = "it should have email format")
    @NotBlank(message = "email cannot be empty")
    @ValidEmail
    private String email;
    @NotBlank(message = "password is not empty")
    @Size(min = 6, message = "password should be minimum of 6 symbols")
    private String password;
    private String confirmPassword;
}
