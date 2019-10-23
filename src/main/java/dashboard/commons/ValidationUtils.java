package dashboard.commons;

import dashboard.entities.ProductTypeGroup;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validateEntity(Object entity) {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        if (violations.size()  > 0) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
}
