package fr.pnpc.project.models.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.pnpc.project.models.validation.PhoneNumber;
import fr.pnpc.project.models.validation.PhoneNumberValidator;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by stephen on 25/10/17.
 */

@Entity
@Table(name = "T_USER")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_TOKEN, query = "SELECT u FROM User u WHERE u.authToken = :token"),
        @NamedQuery(name = User.FIND_BY_NICKNAME, query = "SELECT u FROM User u WHERE u.nickname = :nickname"),
        @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u")
})
@Data
public class User implements Serializable {

    /**
     * Name of the named query to find all parameters of a user
     * with his token passed as a parameter.
     */
    public static final String FIND_BY_TOKEN = "User.findByToken";

    /**
     * Name of the named query to find all parameters of a user
     * with his nickname passed as a parameter.
     */
    public static final String FIND_BY_NICKNAME = "User.findByNickname";

    /**
     * Name of the named query to find all the users
     * in the database.
     */
    public static final String FIND_ALL = "User.findAll";

    /**
     * User identifier.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Nickname of the current user..
     * Is unique.
     */
    @NotNull(message = "Nickname should not be null.")
    @Length(min = 5, message = "Nickname must have 5 characters.")
    private String nickname;

    /**
     * Email of current user.
     * Is unique.
     */
    @NotNull(message = "Email should not be null.")
    @Email(message = "Email not valid.")
    private String email;

    /**
     * Phone number of current user.
     */
    @NotNull(message = "Phone number should not be null.")
    @PhoneNumber(message = "Phone number should follow this pattern: " + PhoneNumberValidator.regex)
    private String phoneNumber;

    /**
     * Password of current user.
     */
    @NotNull(message = "Password should not be null.")
    @Length(min = 8, message = "Password must have 8 characters.")
    @JsonIgnore
    private String mdp;

    /**
     * Identification token of current user.
     * It allows the user to use all the resources of the API.
     */
    private String authToken;

    /**
     * Device token of the current user.
     * Token for push notification.
     */
    private String deviceToken;

    /**
     * The list of all the passages of the current user.
     */
    @NotNull(message = "Passage collections should not be null")
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Collection<Passage> passages;

    /**
     * Default constructor
     */
    public User() {
        this.passages = new ArrayList<>();
    }

    /**
     * Constructor
     * To create a builder. @see `Builder`
     * @param builder Builder object.
     */
    private User(Builder builder) {
        this.nickname = builder.nickname;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.mdp = builder.mdp;
        this.passages = new ArrayList<>();
    }

    public static class Builder {
        private String nickname;
        private String email;
        private String phoneNumber;
        private String mdp;

        public User build() {
            return new User(this);
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setMdp(String mdp) {
            this.mdp = mdp;
            return this;
        }
    }
}
