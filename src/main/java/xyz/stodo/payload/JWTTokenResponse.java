package xyz.stodo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTTokenResponse {
    private boolean success;
    private String token;
}
