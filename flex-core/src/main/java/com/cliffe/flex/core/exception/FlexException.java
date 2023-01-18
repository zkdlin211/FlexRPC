package com.cliffe.flex.core.exception;

import com.cliffe.flex.core.enums.StatusEnum;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 6:14 下午
 */
public class FlexException extends RuntimeException{

	public FlexException() {
	}

	public FlexException(String message) {
		super(message);
	}

	public FlexException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlexException(Throwable cause) {
		super(cause);
	}

	public FlexException(StatusEnum statusEnum) {
		super(statusEnum.getDescription());
	}
}
