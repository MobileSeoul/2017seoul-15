package com.djunderworld.stm.marketfollower.service.impl;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.MarketFollower;

/**
 * 시장 팔로우 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface MarketFollowerMapper {

	/**
	 * 시장 팔로우 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * @throws Exception
	 * 
	 */
	void insertMarketFollower(MarketFollower marketFollower) throws Exception;

	/**
	 * 시장 팔로우 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * @throws Exception
	 * 
	 */
	void deleteMarketFollower(MarketFollower marketFollower) throws Exception;

}
