package dashboard.commons;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.size()  > 0) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    // Method check null String
    public static boolean isBlank(String string ) {
        return string == null || string.trim().isEmpty();
    }

    // Validate Email
    public static  boolean iEmail(String email) {
        String regex  = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        return Pattern.matches(regex,email);
    }


    // Validate Phone
    public static boolean isPhone(String phone) {
        String regex = "^((09|03|07|08|05)+([0-9]{8})\\b)$";
        return Pattern.matches(regex,phone);
    }

    // Validate fax
    public static boolean isFax(String fax) {
        String regex = "^(\\+?\\d{1,}(\\s?|\\-?)\\d*(\\s?|\\-?)\\(?\\d{2,}\\)?(\\s?|\\-?)\\d{3,}\\s?\\d{3,})$";
        return Pattern.matches(regex,fax);
    }
}
