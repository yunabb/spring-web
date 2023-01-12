package com.sample.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sample.exception.ApplicationException;
import com.sample.utils.SessionUtils;
import com.sample.web.login.LoginUser;

/**
 * 요청핸들러 메소드와 매개변수에 @LoginUser 어노테이션이 있는지 조사한다. <br/>
 * 해당 어노테이션이 있으면 로그인이 필요한 작업을 요청한 것으로 판단하고, 
 * 세션객체에 로그인된 사용자정보(LoginUserInfo 객체)가 저장되어 있는지 확인한다. <br/>
 * 로그인된 사용자정보가 존재하면 true를 반환하고, 로그인된 사용자정보가 존재하지 않으면 ApplicationException 예외를 던진다.
 * @author lee_e
 *
 */
public class LoginCheckHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 요청핸들러 메소드 혹은 요청핸들러 메소드의 매개변수가 @LoginUser를 가지고 있는지 확인한다.
		boolean isLoginRequired = hasMethodAnnotation(handler) || hasParameterAnnotation(handler);
		
		// isLoginRequired가 false면 로그인여부와 상관없이 true를 반환한다 - 요청핸들러 메소드가 실행됨 
		if (!isLoginRequired) {
			return true;
		}
		
		// isLoginRequired가 true면 로그인여부를 검사한다.
		
		// 세션에 로그인된 사용자정보가 존재하지 않으면 예외를 발생시킨다.
		if (SessionUtils.getAttribute("loginUser") == null) {
			throw new ApplicationException("로그인이 필요한 서비스 입니다.");
		}
		// 세션에 로그인된 사용자정보가 존재하면 true를 반환한다 - 요청핸들러 메소드가 실행됨
		return true;
	}
	
	// 요청핸들러 메소드가 @LoginUser을 가지고 있으면 true를 반환한다.
	private boolean hasMethodAnnotation(Object handler) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		return handlerMethod.hasMethodAnnotation(LoginUser.class);
	}
	
	// 요청핸들러 메소드의 매개변수가 @LoginUser을 가지고 있으면 true를 반환한다.
	private boolean hasParameterAnnotation(Object handler) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
		for (MethodParameter methodParameter : methodParameters) {
			return methodParameter.hasParameterAnnotation(LoginUser.class);
		}
		return false;
	}
	
}
