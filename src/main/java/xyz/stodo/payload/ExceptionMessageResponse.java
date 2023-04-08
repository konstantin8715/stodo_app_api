package xyz.stodo.payload;

import lombok.Getter;

@Getter
public class ExceptionMessageResponse {
    private String message;

    public ExceptionMessageResponse(String message) {
        this.message = message;
    }
}
