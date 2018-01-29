package com.djunderworld.stm.market.service;

import java.util.List;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MarketFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.Location;

/**
 * 시장관련 서비스레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface MarketService {

	/**
	 * 시장id를 조건으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @return Market
	 * 
	 * @throws Exception
	 */
	Market selectMarketById(long id, long userId) throws Exception;

	/**
	 * 위도, 경도 기준으로 반경 5KM이내 시장 리스트 검색: 5KM 반경내에 없을 시, 시장 팔로우 수 내림차 순 시장리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param location
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByLocation(Location location) throws Exception;

	/**
	 * 시장 팔로우 수가 높은 순으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 *
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByFollower() throws Exception;

	/**
	 * 키워드 검색(시장이름, 시장주소)으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param keyword
	 * @param userId
	 * @param offset 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	List<Market> selectMarketListByKeywordAndOffset(String keyword, long userId, long offset) throws Exception;

	/**
	 * 시장 아이디로 상인 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param offset
	 * @param userId
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectUserListByIdAndOffset(long id, long offset, long userId) throws Exception;

	/**
	 * 시장 아이디로 스토리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * @return List<Story>
	 * 
	 * @throws Exception
	 */
	List<Story> selectStoryListByIdAndUserIdAndOffset(long id, long userId, long offset) throws Exception;

	/**
	 * 시장 아이디로 스토리 파일 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param type
	 * @param offset
	 * @return List<File>
	 * 
	 * @throws Exception
	 */
	List<File> selectFileListByIdAndTypeAndOffset(long id, int type, long offset) throws Exception;

	
	/**
	 * 시장 팔로우 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * 
	 * @throws Exception
	 */
	void insertMarketFollower(MarketFollower marketFollower) throws Exception;

	
	/**
	 * 시장 팔로우 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * 
	 * @throws Exception
	 */
	void deleteMarketFollower(MarketFollower marketFollower) throws Exception;

}
