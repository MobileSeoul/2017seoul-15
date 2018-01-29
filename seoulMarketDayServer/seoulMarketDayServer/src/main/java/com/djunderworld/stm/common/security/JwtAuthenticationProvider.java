package com.djunderworld.stm.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.djunderworld.stm.common.flag.UserFlag;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * json web token내 메시지(유저 레벨) 검증 및 권한 부여 클래스
 * 
 * @author dongjooKim
 * 
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
	@Value("#{jwt['jwt.principal']}")
	private String PRINCIPAL;

	@Value("#{jwt['jwt.credentials']}")
	private String CREDENTIALS;

	@Value("#{jwt['jwt.secretKey']}")
	private String SECRET_KEY;

	@Value("#{jwt['jwt.message']}")
	private String message;

	private int userLevel;

	/**
	 * 유저 레벨 별 인증 후 권한 부여 함수
	 * 
	 * claims로 부터 유저 레벨 추출 , 레벨별로 권한 부여(일반 유저, 관리자)
	 * 
	 * @author dongjooKim
	 * 
	 * @param authentication
	 * 
	 * @throws AuthenticationException
	 * 
	 * @return Authentication
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		try {
			userLevel = getUserLevel(token);
		} catch (Exception e) {
			throw new AuthenticationServiceException(message);
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		if (userLevel == UserFlag.ROLE_ADMIN) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		authentication = new UsernamePasswordAuthenticationToken(PRINCIPAL, CREDENTIALS, grantedAuthorities);

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	/**
	 * 엑세스 토큰(claims)에서 유저 레벨 검출 함수
	 * 
	 * @author dongjooKim
	 * @param token
	 * @return Integer
	 */
	public int getUserLevel(String token) {
		Claims body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return Integer.valueOf((String) body.get("user_level"));
	}

}
