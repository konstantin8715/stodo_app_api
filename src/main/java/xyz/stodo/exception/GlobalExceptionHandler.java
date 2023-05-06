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

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleIncorrectEmailException(
            IncorrectEmailException incorrectEmailException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(incorrectEmailException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleIncorrectResetPasswordCodeException(
            IncorrectResetPasswordCodeException incorrectResetPasswordCodeException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(incorrectResetPasswordCodeException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleInvalidTokenException(
            InvalidTokenException invalidTokenException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(invalidTokenException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionMessageResponse> handleExpiredTokenException(
            ExpiredTokenException expiredTokenException
    ) {
        ExceptionMessageResponse exceptionMessageResponse =
                new ExceptionMessageResponse(expiredTokenException.getMessage());

        return new ResponseEntity<ExceptionMessageResponse>(exceptionMessageResponse,
                HttpStatus.BAD_REQUEST);
    }
}
