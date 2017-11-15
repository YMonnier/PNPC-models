package fr.pnpc.project.models.exceptions;

/**
 * Created by stephen on 10/11/17.
 */
public class UserNotExistException extends Exception{

    public static final String defaultMessage = "User not exist in databse.";

    public UserNotExistException(String message) {
    }
}
