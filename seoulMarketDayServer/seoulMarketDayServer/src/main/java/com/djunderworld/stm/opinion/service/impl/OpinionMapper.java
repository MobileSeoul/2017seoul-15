package com.djunderworld.stm.opinion.service.impl;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Opinion;

/**
 * 유저 의견 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface OpinionMapper {

	/**
	 * 고객 의견 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param opinion
	 * @throws Exception
	 */
	void insertOpinion(Opinion opinion) throws Exception;

}
