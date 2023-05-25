package xyz.stodo.validation.annotation;

import xyz.stodo.validation.ResetPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResetPasswordValidator.class)
public @interface ResetPasswordMatches {
    String message() default "password do not match";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
