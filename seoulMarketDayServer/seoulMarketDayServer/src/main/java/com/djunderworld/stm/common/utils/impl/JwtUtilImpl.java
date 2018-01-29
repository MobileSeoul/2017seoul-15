package com.djunderworld.stm.common.utils.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.djunderworld.stm.common.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * json web token 관련 서비스 레이어 인터페이스 구현 클래스(json web token 생성 및 파싱)
 * 
 * @author dongjooKim
 */
@Service
public class JwtUtilImpl implements JwtUtil {

	@Value("#{jwt['jwt.secretKey']}")
	private String SECRET_KEY;

	/**
	 * 엑세스 토큰(claims)에서 유저 레벨 검출 함수
	 * 
	 * @author dongjooKim
	 * @param token
	 * @return Integer
	 */
	@Override
	public int getUserLevel(String token) {
		Claims body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		System.out.println(SECRET_KEY);
		return Integer.valueOf((String) body.get("user_level"));
	}

	/**
	 * 유저레벨과 시크릿 키로 엑세스 토큰(claims)생성 함수
	 * 
	 * @author dongjooKim
	 * @param userLevel
	 * @return String
	 */
	@Override
	public String generateToken(int userLevel) {
		Claims claims = Jwts.claims().setSubject("djunderworld");
		claims.put("user_level", userLevel + "");
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

}