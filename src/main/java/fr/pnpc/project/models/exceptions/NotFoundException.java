package fr.pnpc.project.models.exceptions;

/**
 * This exception is raised when an entity does not exist
 * or is not present in the database.
 */
public class NotFoundException extends Exception {
    public NotFoundException(String typeObject) {
        super(typeObject + " does not exist.");
    }
}
