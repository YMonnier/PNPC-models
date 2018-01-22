package fr.pnpc.project.models.exceptions;

import java.util.List;

/**
 * This exception is raised when a parameter
 * or parameters of an entity is not valid.
 */
public class ObjectNotValidException extends Exception {

    /**
     * Default construtor
     * @param errors List of string errors.
     */
    public ObjectNotValidException(List<String> errors) {
        super( String.join(", ", errors));
    }

    /**
     * Constructor
     * @param error string error
     */
    public ObjectNotValidException(String error) {
        super(error);
    }
}
