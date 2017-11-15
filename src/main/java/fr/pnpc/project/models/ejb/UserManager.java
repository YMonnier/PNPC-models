package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.model.User;
import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.*;
import fr.pnpc.project.models.util.ValidatorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
public class UserManager extends ValidatorManager<User> {

    @EJB
    CrudService<User> serviceManager;

    public UserManager() {
        super();
    }

    public User register(User user) throws NotValidException, NullObjectException {
        if (user == null) {
            throw new NullObjectException(NullObjectException.defaultMessage);
        }
        Set<ConstraintViolation<User>> constraintViolations = constraintViolations(user);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new NotValidException(errors);
        }

        return serviceManager.create(user);
    }

    public User login(String nickname, String password) throws NotValidEmailException, NotValidPassword, UserNotExistException {
        User user = new User.Builder()
                            .setNickname(nickname)
                            .setPassword(password)
                            .build();
        Set<ConstraintViolation<User>> constraintNicknameViolations = validator.validateProperty(user, "email");
        Set<ConstraintViolation<User>> constraintPasswordViolations = validator.validateProperty(user, "password");
        if (constraintNicknameViolations.size() > 0){
            throw new NotValidEmailException(constraintNicknameViolations.iterator().next().getMessage());
        }
        else if (constraintPasswordViolations.size() > 0){
            throw new NotValidPassword(constraintPasswordViolations.iterator().next().getMessage());
        }

        List<User> result = serviceManager.findWithNamedQuery("GET_BY_NICKNAME", QueryParameter.with("nickname", nickname).parameters());
        User u = result.get(0);

        if(user != null){
            throw new UserNotExistException(UserNotExistException.defaultMessage);
        }

        return u;
    }
}
