package xyz.stodo.payload;

import lombok.Data;
import xyz.stodo.validation.annotation.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailRequest {
    @Email(message = "it should have email format")
    @NotBlank(message = "email cannot be empty")
    @ValidEmail
    private String email;
}
