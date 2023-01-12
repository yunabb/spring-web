package com.sample.web.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sample.exception.ApplicationException;

// 모든 컨트롤러에서 공통으로 사용하는 어노테이션(주로 예외처리)을 정의한다.
// 주로 예외처리를 목적으로 하는 어노테이션이 많다.
// 일괄적으로 예외처리를 할 수 있다.
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	public String handleApplicationException(ApplicationException ex) {
		ex.printStackTrace();
		return "error/500";
	}
	
	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(DataAccessException ex) {
		ex.printStackTrace();
		return "error/500";
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException ex) {
		ex.printStackTrace();
		return "error/500";
	}
}
