package xyz.stodo.payload;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private final String message = "Mail not confirmed or invalid username and (or) password";
}
