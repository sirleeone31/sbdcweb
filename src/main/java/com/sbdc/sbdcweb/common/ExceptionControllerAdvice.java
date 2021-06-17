package com.sbdc.sbdcweb.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sbdc.sbdcweb.common.domain.response.ExceptionResponse;

/**
 * 예외처리 관리
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-24
 * 
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error("front form 데이터 에러", e.getMessage(), e);

	    ExceptionResponse exceptionResponse = new ExceptionResponse("front form 데이터 에러");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

}
