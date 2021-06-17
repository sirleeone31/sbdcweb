package com.sbdc.sbdcweb.common.domain.response;

import lombok.Data;

/**
 * 예외처리 관리
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-24
 */
@Data
public class ExceptionResponse {
	private String message;

	public ExceptionResponse(String message) {
		this.message = message;
	}
}