package fr.pnpc.project.models;

import fr.pnpc.project.models.model.Waypoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaypointTest {

    private static Validator validator;

    /// Valid Waypoint Coords.
    private final long[][] VALID_POINTS = new long[][]{
            {(long) 12.1, (long) 23.1}
    };
    /// Unvalid Waypoint Coords.
    private final long[][] UNVALID_POINTS = new long[][]{
            {(long) 123550.2342, (long) 12334234.5435}
    };

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void pointsValidTest() {
        Waypoint waypoint = null;
        for (long[] vp : VALID_POINTS) {
            waypoint = new Waypoint.Builder()
                    .setLatitude(vp[0])
                    .setLongitude(vp[1])
                    .build();

            Set<ConstraintViolation<Waypoint>> constraintViolations =
                    validator.validate(waypoint);

            assertEquals(0, constraintViolations.size());
        }
    }

    @Test
    void pointsUnvalidTest() {
        Waypoint waypoint = null;
        for (long[] vp : UNVALID_POINTS) {
             waypoint = new Waypoint.Builder()
                    .setLatitude(vp[0])
                    .setLongitude(vp[1])
                    .build();

            Set<ConstraintViolation<Waypoint>> constraintViolations =
                    validator.validate(waypoint);

            assertEquals(2, constraintViolations.size());
        }
    }
}
