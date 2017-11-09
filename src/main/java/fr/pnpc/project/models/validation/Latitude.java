package fr.pnpc.project.models.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = LatitudeValidator.class)
@Documented
public @interface Latitude {
    // https://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/#section-dynamic-payload
    String message() default "Longitude should be between " + LatitudeValidator.min + "and " + LatitudeValidator.max;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
