package xyz.stodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.stodo.payload.ExceptionMessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO: 4/8/23 Зарефакторить
    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleUserExistException(
            UserExistException userExistException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(userExistException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleSemesterNotFoundException(
            SemesterNotFoundException semesterNotFoundException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(semesterNotFoundException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }
}
