package fr.pnpc.project.models.ejb;

import fr.pnpc.project.models.dao.CrudService;
import fr.pnpc.project.models.exceptions.ObjectNotValidException;
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

    public WaypointManager() {
        super();
    }

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

    public List getAll() {
        return serviceManager.findAll(Waypoint.class);
    }

    public Waypoint getById(int id) {
        return serviceManager.find(Waypoint.class, id);
    }

    public void delete(int id) {
        serviceManager.delete(Waypoint.class, id);
    }

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
