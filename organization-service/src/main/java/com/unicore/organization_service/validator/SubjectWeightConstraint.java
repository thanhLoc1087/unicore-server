package com.unicore.organization_service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = SubjectWeightValidator.class)
@Target(ElementType.TYPE) // Apply at class level
@Retention(RetentionPolicy.RUNTIME)
public @interface SubjectWeightConstraint {
    String message() default "The total weight of all components must be 100.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
