package dashboard.exceptions.customs;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("Không tìm thấy dữ liệu");
    }
}
