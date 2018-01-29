package com.djunderworld.stm.merchantfollower.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.MerchantFollower;

/**
 * 상인 팔로우 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface MerchantFollowerMapper {

	/**
	 * 시장 상인아이디 별 팔로우 수 카운트 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param merchantId
	 * @return Integer
	 * 
	 * @throws Exception
	 */
	int selectMerchantFollowerCountByMerchantId(@Param("merchantId") long merchantId) throws Exception;

	/**
	 * 유저아이디로 해당 상인 팔로우 여부 체크 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param merchantId
	 * @param userId
	 * 
	 * @return Boolean
	 * 
	 * @throws Exception
	 */
	Boolean selectFollowCheckByMerchantIdAndUserId(@Param("merchantId") long merchantId, @Param("userId") long userId)
			throws Exception;

	/**
	 * 상인 팔로우 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	void insertMerchantFollower(MerchantFollower merchantFollower) throws Exception;

	/**
	 * 상인 팔로우 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	void deleteMerchantFollower(MerchantFollower merchantFollower) throws Exception;
}
