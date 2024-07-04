package userstore.userservice.handlers.exceptions;

import java.util.List;

public class MultipleErrorsException extends RuntimeException {
    private List<String> errors;

    public MultipleErrorsException(String attribute, List<String> errors) {
        super(attribute);
        this.errors = errors;
    }

    public MultipleErrorsException(String attribute, List<String> errors, String concat) {
        super(attribute);
        this.errors = errors.stream().map(error -> error + concat).toList();
    }

    public List<String> getErrors() {
        return errors;
    }
}