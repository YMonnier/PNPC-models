package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.LoginNotAllowException;
import fr.pnpc.project.models.exceptions.NotFoundException;
import fr.pnpc.project.models.exceptions.ObjectNotValidException;
import fr.pnpc.project.models.model.User;
import fr.pnpc.project.models.model.Waypoint;
import fr.pnpc.project.models.util.ErrorMessages;
import fr.pnpc.project.models.util.TokenUtil;
import fr.pnpc.project.models.util.ValidatorManager;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Data
@Stateless
public class UserManager extends ValidatorManager<User> implements Serializable {

    @Inject
    CrudService<User> serviceManager;

    /**
     * Default constructor
     * Is require when the constructor is instanciate during
     * the injection
     */
    public UserManager() {
        super();
    }

    /**
     * Function register a user in database.
     * Need a valid user verify by ConstraintValidation.
     * Nickname need to be unique.
     * The password is cipher with BCrypt librairie.
     *
     * If the function throws a Exception, a rollback is trigger in database.
     *
     * @param user User to be register.
     * @return User object with his identifier.
     * @throws ObjectNotValidException
     */
    @Transactional(rollbackOn = {Exception.class})
    public User register(User user) throws ObjectNotValidException {
        if (user == null) {
            throw new ObjectNotValidException(ErrorMessages.NULL_OBJECT);
        }
        Set<ConstraintViolation<User>> constraintViolations = constraintViolations(user);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new ObjectNotValidException(errors);
        }

        String hashedPassword = BCrypt.hashpw(user.getMdp(), BCrypt.gensalt());
        user.setMdp(hashedPassword);

        return serviceManager.create(user);
    }

    /**
     * Function login user with his nickname and his cipher password.
     * Nickname and password are verify with ConstraintValidation.
     * A token is generate with java-jwt librairie.
     *
     * If the function throws a Exception, a rollback is trigger in database.
     *
     * @param nickname User nickname.
     * @param password User password.
     * @return User object.
     * @throws Exception
     */
    @Transactional(rollbackOn = {ObjectNotValidException.class, NotFoundException.class})
    public User login(String nickname, String password) throws Exception {
        User user = new User.Builder()
                .setNickname(nickname)
                .setMdp(password)
                .build();
        Set<ConstraintViolation<User>> constraintNicknameViolations = validator.validateProperty(user, "nickname");
        Set<ConstraintViolation<User>> constraintPasswordViolations = validator.validateProperty(user, "mdp");
        if (constraintNicknameViolations.size() > 0) {
            throw new ObjectNotValidException(constraintNicknameViolations.iterator().next().getMessage());
        } else if (constraintPasswordViolations.size() > 0) {
            throw new ObjectNotValidException(constraintPasswordViolations.iterator().next().getMessage());
        }

        List<User> result = serviceManager.findWithNamedQuery(User.FIND_BY_NICKNAME, QueryParameter.with("nickname", nickname).parameters());
        User u = result.get(0);

        if (u == null) {
            throw new NotFoundException("User " + nickname);
        }
        if (BCrypt.checkpw(password, u.getMdp())) {
            try {
                u.setAuthToken(TokenUtil.generate(u));
                serviceManager.update(u);
            } catch (UnsupportedEncodingException e) {
                throw new Exception(e.getMessage());
            }
        } else {
            u.setAuthToken(null);
            serviceManager.update(u);
            throw new LoginNotAllowException();
        }

        return u;
    }

    /**
     * Get all user informations by his identifier.
     *
     * If the function throws a Exception, a rollback is trigger in database.
     *
     * @param id User identifier.
     * @return User object.
     * @throws NotFoundException
     */
    public User getById(long id) throws NotFoundException {
        User w = (User)this.serviceManager.find(User.class, id);
        if (w == null) {
            throw new NotFoundException("User " + id);
        } else {
            return w;
        }
    }
}
