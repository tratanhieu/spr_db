package dashboard.exceptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.exceptions.customs.ValidationException;
import dashboard.services.CommonService;
import org.hibernate.exception.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionResolver {

	@Autowired
	CommonService commonService;

	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseEntity handleException(Exception ex) {
		ex.printStackTrace();
		Map<String, String> response = new HashMap<>();
		response.put("errorMessage", commonService.getMessageSource("system.error.undefined"));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
    
    // SQLIntegrityConstraintViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody ResponseEntity dataIntegrityViolationException(DataIntegrityViolationException ex) {
		Map<String, String> errorsReturn = new HashMap<>();
		ConstraintViolationException constraintViolation = (ConstraintViolationException) ex.getCause();
		String[] constraintName = constraintViolation.getConstraintName().split("_");
		if (constraintName.length == 2) {
			errorsReturn.put(constraintName[1], commonService.getMessageSource("message.entity.exist"));
		} else {
			ex.printStackTrace();
		}
		Map<String, Map> response = new HashMap<>();
		response.put("formErrors", errorsReturn);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ResponseEntity resourceNotFoundException() {
		Map<String, String> response = new HashMap<>();
		response.put("errorMessage", commonService.getMessageSource("message.entity.notFound"));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

    // Handle @Valid in services
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public @ResponseBody ResponseEntity handleConstraintViolationExceptions(
			HttpServletRequest request,
			javax.validation.ConstraintViolationException ex
	) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		Map<String, String> mapErrors = new HashMap<>();
		errors.forEach(error -> {
			final String message = error.getMessage();
//			final Locale currentLocale = request.getLocale();
//			final Object[] params = error.getExecutableParameters();
			if (message.startsWith("{") && message.endsWith("}")) {
				mapErrors.put(error.getPropertyPath().toString(),
						commonService.getMessageSource(message.substring(1, message.length() - 1))
				);
			} else {
				mapErrors.put(error.getPropertyPath().toString(), message);
			}
		});

		Map<String, Map> response = new HashMap<>();
		response.put("formErrors", mapErrors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ValidationException.class)
	public @ResponseBody ResponseEntity handleValidationException(
			HttpServletRequest request,
			ValidationException ex
	) {
		Map mapErrors = ex.getErrors();
		Map<String, Map> response = new HashMap<>();
		response.put("formErrors", mapErrors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
