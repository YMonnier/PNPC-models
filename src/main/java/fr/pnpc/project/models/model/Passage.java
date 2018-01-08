package fr.pnpc.project.models.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
public class Passage {

    public final static String FIND_BY_USER_ID = "FIND_PASSAGES_BY_USER_ID";
    public final static String FIND_BY_ID = "FIND_PASSAGES_BY_ID";

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "User object should not be null.")
    private User user;

    @NotNull(message = "Waypoint object should not be null.")
    private Waypoint waypoint;

    public Passage(){}

}