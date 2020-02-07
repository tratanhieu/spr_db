package dashboard.exceptions.customs;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> errors;

    public ValidationException() {}

    public ValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map getErrors() {
        return errors;
    }
}
