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
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {
    // https://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/#section-dynamic-payload
    String message() default "{default message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
