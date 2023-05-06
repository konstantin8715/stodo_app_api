package xyz.stodo.validation;

import xyz.stodo.payload.ResetPasswordRequest;
import xyz.stodo.validation.annotation.PasswordMatches;
import xyz.stodo.validation.annotation.ResetPasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResetPasswordValidator implements ConstraintValidator<ResetPasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        ResetPasswordRequest resetPasswordRequest = (ResetPasswordRequest) o;
//        System.out.println("password validator");
        return resetPasswordRequest
                .getPassword()
                .equals(resetPasswordRequest.getConfirmPassword());
    }
}
