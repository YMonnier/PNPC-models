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

    @NotNull(message = "Latitude should not be null.")
    @Latitude
    private Long latitude;

    @NotNull(message = "Longitude should not be null.")
    @Longitude
    private Long longitude;

    @NotNull(message = "Passage collections should not be null")
    @ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private Collection<Passage> passages;

    public Waypoint() {
    }

    private Waypoint(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.passages = new ArrayList<>();
    }

    public static class Builder {
        private Long latitude;
        private Long longitude;

        public Waypoint build() {
            return new Waypoint(this);
        }

        public Builder setLatitude(long latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(long longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setLatitude(Long latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Long longitude) {
            this.longitude = longitude;
            return this;
        }
    }
}
