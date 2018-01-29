package com.djunderworld.stm.common.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * json web token 필터 클래스(인증 및 권한부여 기능)
 * 
 * @author dongjooKim
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	@Value("#{jwt['jwt.principal']}")
	private String PRINCIPAL;

	@Value("#{jwt['jwt.header']}")
	private String HEADER;

	@Value("#{jwt['jwt.message']}")
	private String message;

	private UsernamePasswordAuthenticationToken authRequest;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * json web token 인증 필터 생성자
	 * 
	 * URL 요청 매핑, 성공시 핸들러, 인증 매니저 설정
	 * 
	 * @author dongjooKim
	 * 
	 * @param defaultFilterProcessesUrl
	 */
	protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationSuccessHandler(new UrlAuthenticationSuccessHandler());
		setAuthenticationManager(authenticationManager);
	}

	/**
	 * json web token 인증 함수
	 * 
	 * 헤더로 부터 토큰 추출 및, 토큰 정보로 인증요청 토큰 생성 후 인증 반환
	 * 
	 * @author dongjooKim
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws AuthenticationException
	 * @throws IOException
	 * @throws ServletException
	 * 
	 * @return Authentication
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		String token = request.getHeader(HEADER);

		if (token == null) {
			throw new AuthenticationServiceException(message);
		} else {
			authRequest = new UsernamePasswordAuthenticationToken(PRINCIPAL, token);
		}
		return getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * 요청 및 응답 체인 필터링 수행
	 * 
	 * @author dongjooKim
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);
	}

}
