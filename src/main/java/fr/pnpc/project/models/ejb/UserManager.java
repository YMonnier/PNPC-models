package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.*;
import fr.pnpc.project.models.model.User;
import fr.pnpc.project.models.util.ErrorMessages;
import fr.pnpc.project.models.util.TokenUtil;
import fr.pnpc.project.models.util.ValidatorManager;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import sun.tools.jstat.Token;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Stateless
public class UserManager extends ValidatorManager<User> implements Serializable {

    @Inject
    CrudService<User> serviceManager;

    public UserManager() {
        super();
    }

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

        return serviceManager.create(user);
    }

    @Transactional(rollbackOn = {ObjectNotValidException.class, NotFoundException.class})
    public User login(String nickname, String password) throws Exception {
        User user = new User.Builder()
                .setNickname(nickname)
                .setPassword(password)
                .build();
        Set<ConstraintViolation<User>> constraintNicknameViolations = validator.validateProperty(user, "email");
        Set<ConstraintViolation<User>> constraintPasswordViolations = validator.validateProperty(user, "password");
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
                TokenUtil.generate(u);
            } catch (UnsupportedEncodingException e) {
                throw new Exception(e.getMessage());
            }
        }

        return u;
    }
}
