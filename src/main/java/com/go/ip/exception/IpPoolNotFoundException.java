package com.go.ip.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class IpPoolNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IpPoolNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public IpPoolNotFoundException(String message) {
		super(message);
	}

	public IpPoolNotFoundException(Throwable cause) {
		super(cause);
	}

}
