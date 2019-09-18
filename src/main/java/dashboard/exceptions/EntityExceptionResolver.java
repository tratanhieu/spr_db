package dashboard.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionResolver extends ResponseEntityExceptionHandler{

//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	public @ResponseBody ExceptionResponse handleException(Exception ex,
//			HttpServletRequest request) {
//
//		ExceptionResponse error = new ExceptionResponse();
//		error.setMessage("Có lỗi không mong muốn xãy ra. Vui lòng thử lại sau.");
//		return error;
//	}
    
    // SQLIntegrityConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse sqlExceptions(Exception ex,
    		HttpServletRequest request) {
    	
		ExceptionResponse error = new ExceptionResponse();
		error.setType(ex.getCause().toString());
		error.setMessage(ex.getMessage());
		return error;
    }
}
