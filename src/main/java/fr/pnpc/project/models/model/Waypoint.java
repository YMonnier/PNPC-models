package fr.pnpc.project.models.model;

import fr.pnpc.project.models.validation.Latitude;
import fr.pnpc.project.models.validation.Longitude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "T_WAYPOINT")
@Data
public class Waypoint implements Serializable {

    /**
     * Waypoint identifier.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Beacon identifier of current waypoint.
     * Identifier for identify beacon.
     */
    private String beaconId;

    /**
     * Latitude of current waypoint.
     */
    @NotNull(message = "Latitude should not be null.")
    @Latitude
    private Double latitude;

    /**
     * Longitude of current waypoint.
     */
    @NotNull(message = "Longitude should not be null.")
    @Longitude
    private Double longitude;

    /**
     * The list of all the waypoints of the current waypoint.
     */
    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.PERSIST)
    private Collection<Passage> passages;

    /**
     * Default constructor
     */
    public Waypoint() {
        this.passages = new ArrayList<>();
    }

    /**
     * Constructor
     * To create a builder. @see `Builder`
     * @param builder builder Builder object.
     */
    private Waypoint(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.passages = new ArrayList<>();
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;

        public Waypoint build() {
            return new Waypoint(this);
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }
    }
}
