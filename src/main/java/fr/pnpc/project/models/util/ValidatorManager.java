package fr.pnpc.project.models.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorManager<T> implements fr.pnpc.project.models.util.Validator<T> {
    protected Validator validator;

    public ValidatorManager() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    public Set<ConstraintViolation<T>> constraintViolations(T object) {
        return validator.validate(object);
    }
}
