package dashboard.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionResolver {

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ExceptionResponse handleException(Exception ex,
									  HttpServletRequest request) {
		ex.printStackTrace();
		ExceptionResponse error = new ExceptionResponse();
		error.setMessage(ex.getMessage());
		return error;
	}
    
    // SQLIntegrityConstraintViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody Map<String, String> dataIntegrityViolationException(DataIntegrityViolationException ex) {
		Map<String, String> errorsReturn = new HashMap<>();
		ConstraintViolationException constraintViolation = (ConstraintViolationException) ex.getCause();
		String[] constraintName = constraintViolation.getConstraintName().split("_");
		errorsReturn.put("error_type", "form");
		if (constraintName.length == 2) {
			errorsReturn.put(constraintName[1], "Dữ liệu này đã tồn tại");
		}
		return errorsReturn;
    }
//
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public @ResponseBody Map<String, String> handleValidationExceptions(
//			MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//		return errors;
//	}
//
	@ExceptionHandler(ConstraintViolationException.class)
	public @ResponseBody Map<String, String> handleConstraintViolationExceptions(
			javax.validation.ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		Map<String, String> errorsReturn = new HashMap<>();
		errorsReturn.put("error_type", "form");
		errors.forEach(error -> errorsReturn.put(error.getPropertyPath().toString(), error.getMessage()));
		return errorsReturn;
	}
}
