package xyz.stodo.payload;

import lombok.Data;
import xyz.stodo.validation.annotation.ResetPasswordMatches;
import xyz.stodo.validation.annotation.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ResetPasswordMatches
public class ResetPasswordRequest {
    @Email(message = "it should have email format")
    @NotBlank(message = "email cannot be empty")
    @ValidEmail
    private String email;
    @NotBlank(message = "reset code cannot be empty")
    private String code;
    @NotBlank(message = "password is not empty")
    @Size(min = 6, message = "password should be minimum of 6 symbols")
    private String password;
    private String confirmPassword;
}
