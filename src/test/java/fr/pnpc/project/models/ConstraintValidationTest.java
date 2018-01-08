package fr.pnpc.project.models;

import fr.pnpc.project.models.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstraintValidationTest {

    private static final String VALID_EMAIL = "test@test.com";
    private static final String NOT_VALID_EMAIL = "test.com";

    private static Validator validator;

    private User user;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validEmailConstraintValidationTest(){
        user = new User.Builder().setEmail(VALID_EMAIL).build();

        Set<ConstraintViolation<User>> constraintEmailViolations = validator.validateProperty(user, "email");
        assertEquals(0, constraintEmailViolations.size());
    }

    @Test
    public void notValidEmailConstraintValidationTest(){
        user = new User.Builder().setEmail(NOT_VALID_EMAIL).build();

        Set<ConstraintViolation<User>> constraintEmailViolations = validator.validateProperty(user, "email");
        assertEquals(1, constraintEmailViolations.size());
    }

}
