package com.djunderworld.stm.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * json web token 에러 및 유저 인증 에러 시, 에러 response 클래스
 * 
 * @author dongjooKim
 * 
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Value("#{jwt['jwt.message']}")
	private String message;

	/**
	 * 인증 예외 관련 에러메시지 송출 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param request
	 * @param response
	 * @param authException
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
	}

}
