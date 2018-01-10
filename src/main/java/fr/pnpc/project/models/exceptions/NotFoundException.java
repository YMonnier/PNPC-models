package fr.pnpc.project.models.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String typeObject) {
        super(typeObject + " does not exist.");
    }
}
