package dashboard.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionResolver {

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
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ExceptionResponse sqlExceptions(Exception ex,
    		HttpServletRequest request) {
    	
		ExceptionResponse error = new ExceptionResponse();
		error.setType(ex.getCause().toString());
		error.setMessage(ex.getMessage());
		return error;
    }
}
