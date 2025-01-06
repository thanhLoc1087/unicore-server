package com.unicore.identity.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateOfBirthValidator.class})
public @interface DateOfBirthConstraint {
    String message() default "Invalid date of birth.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min();
}
