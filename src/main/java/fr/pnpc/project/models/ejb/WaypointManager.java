package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.dao.QueryParameter;
import fr.pnpc.project.models.exceptions.NotFoundException;
import fr.pnpc.project.models.exceptions.ObjectNotValidException;
import fr.pnpc.project.models.model.User;
import fr.pnpc.project.models.model.Waypoint;
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

/**
 * Created by stephen on 10/11/17.
 */

@Stateless
@Transactional
public class WaypointManager extends ValidatorManager<Waypoint> implements Serializable {

    @Inject
    CrudService<Waypoint> serviceManager;

    /**
     * Default constructor
     * Is require when the constructor is instanciated during
     * the injection
     */
    public WaypointManager() {
        super();
    }

    /**
     * Persist a Waypoint object in database.
     * Need a valid waypoint checked by `ConstraintValidation`.
     *
     * If the function throws a Exception, a rollback is trigged in database.
     *
     * @param waypoint Waypoint object.
     * @return Waypoint with database identifier.
     * @throws ObjectNotValidException
     */
    @Transactional(rollbackOn = {ObjectNotValidException.class})
    public Waypoint create(Waypoint waypoint) throws ObjectNotValidException {
        if (waypoint == null) {
            throw new ObjectNotValidException(ErrorMessages.NULL_OBJECT);
        }
        Set<ConstraintViolation<Waypoint>> constraintViolations = constraintViolations(waypoint);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new ObjectNotValidException(errors);
        }

        return serviceManager.create(waypoint);
    }

    /**
     * Get all waypoint in database.
     *
     * @return List of waypoint object.
     */
    public List getAll() {
        return serviceManager.findAll(Waypoint.class);
    }

    public Waypoint getById(String beaconId) throws NotFoundException {
        List<Waypoint> result = serviceManager.findWithNamedQuery(Waypoint.FIND_BY_BEACONID, QueryParameter.with("beaconId", beaconId).parameters());
        Waypoint w = result.get(0);

        if (w == null) {
            throw new NotFoundException("Waypoint " + beaconId);
        }

        return w;
    }

    /**
     * Delete a waypoint object by identifier.
     *
     * @param id Waypoint identifier.
     * @throws NotFoundException
     */
    public void delete(long id) throws NotFoundException {
        Waypoint w = serviceManager.find(Waypoint.class, id);

        if (w == null) {
            throw new NotFoundException("Waypoint " + id);
        }
        serviceManager.delete(Waypoint.class, id);
    }

    /**
     * Update a waypoint parameters.
     * Need a valid newWaypoint checked by `ConstraintValidation`.
     *
     * If the function throws a Exception, a rollback is trigged in database.
     *
     * @param newWaypoint Waypoint object.
     * @return Waypoint updated.
     * @throws ObjectNotValidException
     */
    @Transactional(rollbackOn = {ObjectNotValidException.class})
    public Waypoint update(Waypoint newWaypoint) throws ObjectNotValidException {
        if (newWaypoint == null) {
            throw new ObjectNotValidException(ErrorMessages.NULL_OBJECT);
        }
        Set<ConstraintViolation<Waypoint>> constraintViolations = constraintViolations(newWaypoint);
        if (constraintViolations.size() > 0) {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(ucv -> errors.add(ucv.getMessage()));
            throw new ObjectNotValidException(errors);
        }

        return serviceManager.update(newWaypoint);
    }
}
