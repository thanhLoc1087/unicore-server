package com.unicore.organization_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.unicore.organization_service.entity.SubjectMetadata;

public class SubjectWeightValidator implements ConstraintValidator<SubjectWeightConstraint, SubjectMetadata> {

    @Override
    public boolean isValid(SubjectMetadata examWeights, ConstraintValidatorContext context) {
        if (examWeights == null) {
            return true; // Null object is valid by default
        }

        int totalWeight = examWeights.getCourseworkWeight()
                + examWeights.getMidtermWeight()
                + examWeights.getPracticalWeight()
                + examWeights.getFinalWeight();

        return totalWeight == 100;
    }
}
