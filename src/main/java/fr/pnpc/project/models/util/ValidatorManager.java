package fr.pnpc.project.models.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class ValidatorManager<T> {
    protected Validator validator;

    public ValidatorManager() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    protected Set<ConstraintViolation<T>> constraintViolations(T object) {
        return validator.validate(object);
    }
}
