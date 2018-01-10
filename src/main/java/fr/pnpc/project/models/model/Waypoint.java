package fr.pnpc.project.models.model;

import fr.pnpc.project.models.validation.Latitude;
import fr.pnpc.project.models.validation.Longitude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "T_WAYPOINT")
@Data
public class Waypoint {

    @Id
    @GeneratedValue
    private long id;

    private String beaconId;

    @NotNull(message = "Latitude should not be null.")
    @Latitude
    private Double latitude;

    @NotNull(message = "Longitude should not be null.")
    @Longitude
    private Double longitude;

    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.PERSIST)
    private Collection<Passage> passages;

    public Waypoint() {
        this.passages = new ArrayList<>();
    }

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
