package dashboard.exceptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import dashboard.exceptions.customs.ResourceNotFoundException;
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
	public @ResponseBody ResponseEntity handleException(
			Exception ex,
			HttpServletRequest request
	) {
		ex.printStackTrace();

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ExceptionResponse(commonService.getMessageSource("system.error.undefined")));
	}
    
    // SQLIntegrityConstraintViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody ResponseEntity dataIntegrityViolationException(DataIntegrityViolationException ex) {
		Map<String, String> errorsReturn = new HashMap<>();
		ConstraintViolationException constraintViolation = (ConstraintViolationException) ex.getCause();
		String[] constraintName = constraintViolation.getConstraintName().split("_");
		if (constraintName.length == 2) {
			errorsReturn.put(constraintName[1], commonService.getMessageSource("message.entity.exist"));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorsReturn);
    }

	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ResponseEntity<ExceptionResponse> resourceNotFoundException() {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(new ExceptionResponse(commonService.getMessageSource("message.entity.notFound")));
	}

    // Handle @Valid in services
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public @ResponseBody ResponseEntity handleConstraintViolationExceptions(
			HttpServletRequest request,
			javax.validation.ConstraintViolationException ex
	) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		Map<String, String> errorsReturn = new HashMap<>();
		errors.forEach(error -> {
			final String message = error.getMessage();
//			final Locale currentLocale = request.getLocale();
//			final Object[] params = error.getExecutableParameters();
			if (message.startsWith("{") && message.endsWith("}")) {
				errorsReturn.put(error.getPropertyPath().toString(),
						commonService.getMessageSource(message.substring(1, message.length() - 1))
				);
			} else {
				errorsReturn.put(error.getPropertyPath().toString(), message);
			}
		});

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorsReturn);
	}
}
