package fr.pnpc.project.models.exceptions;

/**
 * This exception is raised when a user wants
 * to log on to an application and their nickname or password is invalid.
 */
public class LoginNotAllowException extends Exception {
    public LoginNotAllowException() {
        super("Bad credentials.");
    }
}
