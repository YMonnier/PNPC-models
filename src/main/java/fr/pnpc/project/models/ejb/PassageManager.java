package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.NotFoundException;
import fr.pnpc.project.models.exceptions.ObjectNotValidException;
import fr.pnpc.project.models.model.Passage;
import fr.pnpc.project.models.util.ErrorMessages;
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

    /**
     * Default constructor
     * Is require when the constructor is instanciated during
     * the injection
     */
    public PassageManager() {
        super();
    }

    /**
     * Persist a Passage object in database
     * All parameters of passage are verify by `ConstraintViolation` object.
     * If the function throws a Exception, a rollback is trigged in database.
     * @param passage Passage object.
     * @return Passage passage object.
     * @throws ObjectNotValidException
     */
    @Transactional(rollbackOn = {ObjectNotValidException.class})
    public Passage create(Passage passage) throws ObjectNotValidException {
        if (passage == null) {
            throw new ObjectNotValidException(ErrorMessages.NULL_OBJECT);
        }

        Set<ConstraintViolation<Passage>> constraintViolations = constraintViolations(passage);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new ObjectNotValidException(errors);
        }

        return serviceManager.create(passage);
    }

    /**
     * Return all passages by a user identifier.
     * If the function throws a Exception, a rollback is trigged in database.
     *
     * @param id User identifier.
     * @return List Passage object.
     * @throws NotFoundException
     */
    @Transactional(rollbackOn = {NotFoundException.class})
    public List getPassagesByUserId(long id) throws NotFoundException {
        List<Passage> passages = serviceManager.findWithNamedQuery(Passage.FIND_BY_USER_ID, QueryParameter.with("id", id).parameters());

        if (passages == null) {
            throw new NotFoundException("Passage " + id);
        }

        return passages;
    }

    /**
     * Return a Passage object who match with the identifier of passage
     * and the identifier of user.
     * If the function throws a Exception, a rollback is trigged in database.
     *
     * @param userId User identifier.
     * @param passageId Passage identifier.
     * @return Passage object
     * @throws NotFoundException
     */
    @Transactional(rollbackOn = {NotFoundException.class})
    public Passage getPassage(long userId, long passageId) throws NotFoundException {
        List<Passage> passages = serviceManager.findWithNamedQuery(Passage.FIND_BY_ID, QueryParameter.with("passageId", passageId).and("userId", userId).parameters());

        if (passages == null) {
            throw new NotFoundException("Passage " + passageId);
        }

        return passages.get(0);
    }

}
