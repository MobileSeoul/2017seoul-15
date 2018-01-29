package com.djunderworld.stm.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 유저 인증 및 권한부여 성공 핸들러(URL redirect) 클래스
 * 
 * @author dongjooKim
 */
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	/**
	 * 컨텍스트에 따른 타켓 url 설정 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param request
	 * @param response
	 * @return String
	 */
	public String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String context = request.getContextPath();
		String fullURL = request.getRequestURI();
		String url = fullURL.substring(fullURL.indexOf(context) + context.length());
		return url;
	}

	/**
	 * 인증 성공 핸들링 함수 
	 * 
	 * 요청에 따른 타겟 Url로 디스페칭 및 포워딩
	 * 
	 * @author dongjooKim
	 * 
	 * @param request
	 * @param response
	 * @param authentication
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String url = determineTargetUrl(request, response);
		request.getRequestDispatcher(url).forward(request, response);
	}
}
