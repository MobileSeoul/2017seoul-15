package com.djunderworld.stm.market.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MarketFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.Location;
import com.djunderworld.stm.file.service.impl.FileMapper;
import com.djunderworld.stm.market.service.MarketService;
import com.djunderworld.stm.marketfollower.service.impl.MarketFollowerMapper;
import com.djunderworld.stm.story.service.impl.StoryMapper;
import com.djunderworld.stm.user.service.impl.UserMapper;

/**
 * 시장 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketMapper marketMapper;

	@Autowired
	private StoryMapper storyMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MarketFollowerMapper marketFollowerMapper;

	/**
	 * 시장id를 조건으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param userId
	 * @return Market
	 * 
	 * @throws Exception
	 */
	@Override
	public Market selectMarketById(long id, long userId) throws Exception {
		return marketMapper.selectMarketById(id, userId);
	}

	/**
	 * 위도, 경도 기준으로 반경 5KM이내 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param location
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Market> selectMarketListByLocation(Location location) throws Exception {
		List<Market> markets = marketMapper.selectMarketListByLocationLimitFive(location);
		return markets;
	}

	/**
	 * 시장 팔로우 수 내림차 순 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Market> selectMarketListByFollower() throws Exception {
		return marketMapper.selectMarketListByFollowerLimitFive();
	}

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
	@Override
	public List<Market> selectMarketListByKeywordAndOffset(String keyword, long userId, long offset) throws Exception {
		return marketMapper.selectMarketListByKeywordAndOffset(keyword, userId, offset);
	}

	/**
	 * 시장 아이디로 상인 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param offset
	 * @return List<User>
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public List<User> selectUserListByIdAndOffset(long id, long offset, long userId) throws Exception {
		return userMapper.selectMerchantListByMarketIdAndOffset(id, offset, userId);
	}

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
	@Override
	public List<Story> selectStoryListByIdAndUserIdAndOffset(long id, long userId, long offset) throws Exception {
		return storyMapper.selectStoryListByMarketIdAndUserIdAndOffset(id, userId, offset);
	}

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
	@Override
	public List<File> selectFileListByIdAndTypeAndOffset(long id, int type, long offset) throws Exception {
		return fileMapper.selectFileListByMarketIdAndTypeAndOffset(id, type, offset);
	}

	/**
	 * 시장 팔로우 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * 
	 * @throws Exception
	 */
	@Override
	public void insertMarketFollower(MarketFollower marketFollower) throws Exception {
		marketFollowerMapper.insertMarketFollower(marketFollower);
	}

	/**
	 * 시장 팔로우 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketFollower
	 * 
	 * @throws Exception
	 */
	@Override
	public void deleteMarketFollower(MarketFollower marketFollower) throws Exception {
		marketFollowerMapper.deleteMarketFollower(marketFollower);
	}

}
