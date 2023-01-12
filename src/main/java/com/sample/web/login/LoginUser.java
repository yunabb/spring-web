package com.sample.web.login;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 세션에 저장된 로그인된 사용자정보(LoginUserInfo객체)를 전달받는 매개변수(loginUserInfo)에 이 어노테이션(@LoginUser)을 지정한다.
 * @author yuu
 *
 */
@Documented
// 이 두가지는 꼭 필요한 값이다.
@Retention(RUNTIME)
@Target({PARAMETER, ElementType.METHOD})
public @interface LoginUser {
	
	/**
	 * 어노테이션의 속성이다. 
	 * required 속성이 true로 지정되어 있는 경우, 반드시 LoginUserInfo객체를 제공받아야 한다. 
	 * @return 로그인 정보 필요 여부
	 */
	boolean required() default true;

}

/*
 * @Controller
 * public class UserController {
 * 		@PostMapping("/password")
 * 		public String changePassword(@LoginUser LoginUserInfo loginUserInfo) {
 * 		
 * 		}
 * }
 */
