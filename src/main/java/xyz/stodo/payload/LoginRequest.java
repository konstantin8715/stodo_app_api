package xyz.stodo.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "email cannot be empty")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;
}
