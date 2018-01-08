package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.NotValidException;
import fr.pnpc.project.models.exceptions.NullObjectException;
import fr.pnpc.project.models.exceptions.PassageNotExistException;
import fr.pnpc.project.models.exceptions.UserNotExistException;
import fr.pnpc.project.models.model.Passage;
import fr.pnpc.project.models.util.ValidatorManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
@Transactional
public class PassageManager extends ValidatorManager<Passage> implements Serializable  {

    @Inject
    CrudService<Passage> serviceManager;

    public PassageManager() {
        super();
    }

    @Transactional(rollbackOn = {NullObjectException.class, NotValidException.class})
    public Passage create(Passage passage) throws NullObjectException, NotValidException {
        if (passage == null) {
            throw new NullObjectException(NullObjectException.defaultMessage);
        }

        Set<ConstraintViolation<Passage>> constraintViolations = constraintViolations(passage);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new NotValidException(errors);
        }

        return serviceManager.create(passage);
    }

    @Transactional(rollbackOn = {PassageNotExistException.class})
    public List getPassagesByUserId(int id) throws PassageNotExistException {
        List<Passage> passages = serviceManager.findWithNamedQuery(Passage.FIND_BY_USER_ID, QueryParameter.with("id", id).parameters());

        if (passages != null) {
            throw new PassageNotExistException(UserNotExistException.defaultMessage);
        }

        return passages;
    }

    @Transactional(rollbackOn = {PassageNotExistException.class})
    public Passage getPassage(int userId, int passageId) throws PassageNotExistException {
        List<Passage> passages = serviceManager.findWithNamedQuery(Passage.FIND_BY_ID, QueryParameter.with("passageId", passageId).and("userId", userId).parameters());

        if (passages != null) {
            throw new PassageNotExistException(UserNotExistException.defaultMessage);
        }

        return passages.get(0);
    }

}
