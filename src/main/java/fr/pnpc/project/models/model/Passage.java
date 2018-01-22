package fr.pnpc.project.models.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by stephen on 27/10/17.
 */

@Entity
@Table(name = "T_PASSAGE")
@NamedQueries({
        @NamedQuery(name = Passage.FIND_BY_USER_ID, query = "SELECT p FROM Passage p WHERE p.user.id = :id"),
        @NamedQuery(name = Passage.FIND_BY_ID, query = "SELECT p FROM Passage p WHERE p.id = :passageId AND p.user.id = :userId")
})

@Data
public class Passage implements Serializable {

    /**
     * Name of the named query to find all passages
     * with user identifier passed in parameter.
     */
    public final static String FIND_BY_USER_ID = "FIND_PASSAGES_BY_USER_ID";

    /**
     * Name of the named query to find a passage
     * with his identifier passed in parameter.
      */
    public final static String FIND_BY_ID = "FIND_PASSAGES_BY_ID";

    /**
     * Passage identifier.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * The user who owns the current passage*
     */
    @NotNull(message = "User object should not be null.")
    private User user;

    /**
     * The waypoint who owns the current passage
     */
    @NotNull(message = "Waypoint object should not be null.")
    private Waypoint waypoint;

    /**
     * Default constructor
     * Is require when the constructor is instanciate during
     * the injection
     */
    public Passage(){}

    /**
     * Constructor
     * @param user User object.
     * @param waypoint Waypoint object.
     */
    public Passage(User user, Waypoint waypoint) {
        this.user = user;
        this.waypoint = waypoint;
    }
}