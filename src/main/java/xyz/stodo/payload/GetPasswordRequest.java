package xyz.stodo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class GetPasswordRequest {
    private String email;
}
