package fr.pnpc.project.models.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator
        implements ConstraintValidator<Longitude, Double> {

    public static final int min = -180;
    public static final int max = 180;
    //@Length(min = -180, max = 180, message = )

    @Override
    public void initialize(Longitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double l, ConstraintValidatorContext constraintValidatorContext) {
        if (l == null)
            return true;
        return l >= min && l <= max;
    }
}
