package xyz.stodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SemesterNotFoundException extends RuntimeException {
    public SemesterNotFoundException(String message) {
        super(message);
    }
}
