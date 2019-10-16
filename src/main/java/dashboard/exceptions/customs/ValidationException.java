package dashboard.exceptions.customs;

import dashboard.exceptions.ErrorEntity;

import java.util.List;

public class ValidationException extends Exception {

    private ErrorEntity error;
    private List<ErrorEntity> errors;

    public ValidationException() {

    }

    public ValidationException(String target, String message) {
        this.error.setTarget(target);
        this.error.setMessage(message);
    }

    public ValidationException(List<ErrorEntity> errors) {
        this.errors = errors;
    }

    public ErrorEntity getError() {
        return error;
    }

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public List<ErrorEntity> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorEntity> errors) {
        this.errors = errors;
    }
}
