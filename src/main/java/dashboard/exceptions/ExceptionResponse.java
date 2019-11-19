package dashboard.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResponse {

	@JsonProperty(value = "error_type")
	private String errorType;
	private String message;

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(String message) {
		this.errorType = "modal";
		this.message = message;
	}

	public ExceptionResponse(String errorType, String message) {
		this.errorType = errorType;
		this.message = message;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
