package fr.pnpc.project.models.exceptions;

import java.util.List;

public class ObjectNotValidException extends Exception {

    public ObjectNotValidException(List<String> errors) {
        super( String.join(", ", errors));
    }

    public ObjectNotValidException(String error) {
        super(error);
    }
}
