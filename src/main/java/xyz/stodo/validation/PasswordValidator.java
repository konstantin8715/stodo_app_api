package xyz.stodo.validation;

import xyz.stodo.payload.SignUpRequest;
import xyz.stodo.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignupRequest = (SignUpRequest) o;
        return userSignupRequest
                .getPassword()
                .equals(userSignupRequest.getConfirmPassword());
    }
}
