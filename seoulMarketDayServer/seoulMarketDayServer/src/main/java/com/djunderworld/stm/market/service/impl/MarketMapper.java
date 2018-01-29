package com.djunderworld.stm.market.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dto.Location;

/**
 * 시장 관련 레파지토리레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface MarketMapper {

	/**
	 * 시장 아이디로 시장 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @return Market
	 * 
	 * @throws Exception
	 */
	Market selectMarketById(@Param("id") long id, @Param("userId") long userId) throws Exception;

	/**
	 * 위치(위도, 경도)DTO 로 반경 5KM 이내 시장 리스트 5개 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param location
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByLocationLimitFive(Location location) throws Exception;

	/**
	 * 시장 팔로우 많은 순으로 시장 리스트 5개 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByFollowerLimitFive() throws Exception;

	/**
	 * 키워드 검색으로 시장리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param keyword
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByKeywordAndOffset(@Param("keyword") String keyword, @Param("userId") long userId,
			@Param("offset") long offset) throws Exception;

	/**
	 * 유저(상인)아이디로 소속 시장 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param userId
	 * 
	 * @return Market
	 * 
	 * @throws Exception
	 */
	Market selectMarketByUserId(@Param("userId") long userId) throws Exception;

	/**
	 * 지역구 별로 위도, 경도에 따라 시장 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param regionCategoryId
	 * @param latitude
	 * @param longitude
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByRegionCategoryIdAndLocation(@Param("regionCategoryId") long regionCategoryId,
			@Param("latitude") double latitude, @Param("longitude") double longitude) throws Exception;

	/**
	 * 지역구 별, 시장 유형(1.일반시장, 2.전문시장) 별 시장 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param regionCategoryId
	 * @param marketCategoryId
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByRegionCategoryId(@Param("regionCategoryId") long regionCategoryId,
			@Param("marketCategoryId") long marketCategoryId) throws Exception;

	/**
	 * 유저 아이디 별 팔로우하는 시장 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectFollowingMarketListByUserIdAndOffset(@Param("userId") long userId, @Param("offset") long offset)
			throws Exception;

}
