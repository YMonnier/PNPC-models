package fr.pnpc.project.models.exceptions;

import java.util.List;

public class NotValidException extends Exception {
    private List<String> errors;

    public NotValidException(List<String> errors) {
        super();
        this.errors = errors;
    }
}
