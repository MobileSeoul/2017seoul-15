package com.djunderworld.stm.common.utils;

/**
 * json web token 관련 서비스 레이어 (json web token 생성 및 파싱) 인터페이스
 * 
 * @author dongjooKim
 */
public interface JwtUtil {

	/**
	 * 엑세스 토큰(claims)에서 유저 레벨 검출 함수
	 * 
	 * @author dongjooKim
	 * @param token
	 * @return Integer
	 */
	int getUserLevel(String token);

	/**
	 * 유저레벨과 시크릿 키로 엑세스 토큰(claims)생성 함수
	 * 
	 * @author dongjooKim
	 * @param userLevel
	 * @return String
	 */
	String generateToken(int userLevel);
}
