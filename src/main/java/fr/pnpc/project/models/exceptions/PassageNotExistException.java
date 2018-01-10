package fr.pnpc.project.models.exceptions;

public class PassageNotExistException extends Exception {

    public static final String defaultMessage = "Passage not exist in database.";

    public PassageNotExistException(String message) {
    }
}
