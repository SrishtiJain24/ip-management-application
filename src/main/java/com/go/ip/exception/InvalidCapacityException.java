package com.go.ip.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class InvalidCapacityException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;

	public InvalidCapacityException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCapacityException(String message) {
		super(message);
	}

	public InvalidCapacityException(Throwable cause) {
		super(cause);
	}


}
