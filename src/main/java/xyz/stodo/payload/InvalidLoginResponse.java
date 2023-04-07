package xyz.stodo.payload;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private final String message = "Invalid username and (or) password";
}
