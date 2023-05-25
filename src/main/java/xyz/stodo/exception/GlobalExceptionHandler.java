package xyz.stodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.stodo.payload.ExceptionMessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleException(
            RuntimeException ex
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(ex.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }
}
