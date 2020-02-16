package dashboard.exceptions.customs;

public class InvalidException extends RuntimeException {
    private String message;

    public InvalidException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
