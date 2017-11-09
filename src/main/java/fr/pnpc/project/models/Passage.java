package fr.pnpc.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by stephen on 27/10/17.
 */

@Entity
@Table(name = "T_PASSAGE")
@Data
public class Passage {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "User object should not be null.")
    private User user;

    @NotNull(message = "Waypoint object should not be null.")
    private Waypoint waypoint;

    public Passage(){}

}
