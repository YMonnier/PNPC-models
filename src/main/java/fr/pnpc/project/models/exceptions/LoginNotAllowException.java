package fr.pnpc.project.models.exceptions;

public class LoginNotAllowException extends Exception {
    public LoginNotAllowException() {
        super("Bad credentials.");
    }
}
