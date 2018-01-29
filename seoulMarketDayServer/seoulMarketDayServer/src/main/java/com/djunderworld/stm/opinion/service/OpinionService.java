package com.djunderworld.stm.opinion.service;

import com.djunderworld.stm.common.dao.Opinion;

/**
 * 유저 의견 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface OpinionService {

	/**
	 * 유저 의견 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param opinion
	 * @throws Exception
	 */
	void insertOpinion(Opinion opinion) throws Exception;

}
