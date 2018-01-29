package com.djunderworld.stm.marketuser.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Market;

/**
 * 시장 상인 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface MarketUserMapper {

	/**
	 * 시장 상인 연관관계 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketId
	 * @param userId
	 * 
	 * @throws Exception
	 */
	void insertMarketUser(@Param("marketId") long marketId, @Param("userId") long userId) throws Exception;
}
