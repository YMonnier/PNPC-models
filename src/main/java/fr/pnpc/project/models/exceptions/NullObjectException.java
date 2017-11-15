package fr.pnpc.project.models.exceptions;

public class NullObjectException extends Exception {
    public static final String defaultMessage = "Object should not be null";
    public NullObjectException(String message) {
        super(message);
    }
}
