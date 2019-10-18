package dashboard.exceptions;

public class ErrorEntity {

    private String target;
    private String message;

    public ErrorEntity() {

    }

    public ErrorEntity(String message) {
        this.message = message;
    }

    public ErrorEntity(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
