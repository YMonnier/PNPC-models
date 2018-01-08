package fr.pnpc.project.models.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface Validator<T> {

    Set<ConstraintViolation<T>> constraintViolations(T object);
}
