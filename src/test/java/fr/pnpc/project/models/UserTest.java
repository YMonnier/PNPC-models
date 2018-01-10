package fr.pnpc.project.models;

import fr.pnpc.project.models.model.User;
import fr.pnpc.project.models.validation.PhoneNumberValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private final String DEFAULT_EMAIL = "test@test.com";
    private final String DEFAULT_NICKNAME = "JohnWick";
    private final String DEFAULT_PHONE_NUMBER = "0651576906";
    private final String DEFAULT_PASSWORD = "SUPER PASSWORD";

    private final String[] EMAIL_NOT_VALID = new String[]{
            "zekjdnerkjfe",
            "ezkjdnejknf.fr",
            "azkjn@",
            "@dezdzed.fr",
            "ezkjdezd@.d",
            "@.d",
            "@.de"
    };

    private final String[] PHONE_NUMBER_NOT_VALID = new String[]{
            "01",
            "0123",
            "012345",
            "0123456",
            "01123",
            "01+DZE",
            "01-&é",
            "DZVN1!DG"
    };

    private User user;

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void init() {
        user = new User.Builder()
                .setEmail(DEFAULT_EMAIL)
                .setNickname(DEFAULT_NICKNAME)
                .setPhoneNumber(DEFAULT_PHONE_NUMBER)
                .setPassword(DEFAULT_PASSWORD)
                .build();
    }

    // nickname: @NotNull with a message
    // email: @NotNull with a message, email validation
    // phone number: @NotNull with a message, phone validation
    // password: @NotNull, length >= 8

    @Test
    void UserValidTest() {
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(0, constraintViolations.size());
    }

    // ****
    // Nickname Tests
    // ****
    @Test
    void nicknameNotNullFailedTest() {
        //Assertions.assertTrue(true);
        user.setNickname(null);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Nickname should not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void nicknameLengthToSmallTest() {
        //Assertions.assertTrue(true);
        user.setNickname("John");
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Nickname must have 5 characters.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void nicknameLengthValidTest() {
        //Assertions.assertTrue(true);
        user.setNickname("JohnQU");
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(0, constraintViolations.size());
    }

    // ****
    // Email Tests
    // ****

    @Test
    void emailNotNullFailedTest() {
        user.setEmail(null);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Email should not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void emailNotValidTest() {
        for (String e : EMAIL_NOT_VALID) {
            user.setEmail(e);
            Set<ConstraintViolation<User>> constraintViolations =
                    validator.validate(user);

            assertEquals(1, constraintViolations.size());
            assertEquals("Email not valid.", constraintViolations.iterator().next().getMessage());
        }
    }

    @Test
    void emailNotValid3Test() {
        user.setEmail("zzejkd@.fr");
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Email not valid.", constraintViolations.iterator().next().getMessage());
    }

    // ****
    // Phone number Tests
    // ****

    @Test
    void phoneNumberNotNullFailedTest() {
        user.setPhoneNumber(null);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Phone number should not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void phoneNumberNotValidTest() {
        for (String s : PHONE_NUMBER_NOT_VALID) {
            user.setPhoneNumber(s);
            Set<ConstraintViolation<User>> constraintViolations =
                    validator.validate(user);
            assertEquals(1, constraintViolations.size());
            assertEquals("Phone number should follow this pattern: " + PhoneNumberValidator.regex, constraintViolations.iterator().next().getMessage());
        }
    }

    // ****
    // Password Tests
    // ****

    @Test
    void passwordNotNullFailedTest() {
        user.setMdp(null);
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Password should not be null.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void passwordToSmallTest() {
        user.setMdp("abcdZD");
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        assertEquals(1, constraintViolations.size());
        assertEquals("Password must have 8 characters.", constraintViolations.iterator().next().getMessage());
    }
}
