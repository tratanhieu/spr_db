package dashboard.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public @ResponseBody ExceptionResponse sqlExceptions(Exception ex,
    		HttpServletRequest request) {
    	
		ExceptionResponse error = new ExceptionResponse();
		error.setType(ex.getCause().toString());
		error.setMessage(ex.getMessage());
		return error;
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
			ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		Map<String, String> errorsReturn = new HashMap<>();
		errors.forEach(error -> errorsReturn.put(error.getPropertyPath().toString(), error.getMessage()));
		return errorsReturn;
	}
}
