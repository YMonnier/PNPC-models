package fr.pnpc.project.models.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatitudeValidator
        implements ConstraintValidator<Latitude, Double> {

    public static final int min = -90;
    public static final int max = 90;
    //@Length(min = -90, max = 90, message = "Latitude should be between -90 and 90")

    @Override
    public void initialize(Latitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double l, ConstraintValidatorContext constraintValidatorContext) {
        if (l == null)
            return true;
        return l >= min && l <= max;
    }
}
