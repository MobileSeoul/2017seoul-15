package com.djunderworld.stm.market.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MarketFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.Location;
import com.djunderworld.stm.market.service.MarketService;

/**
 * 시장관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@RestController
@RequestMapping("/markets")
public class MarketController {

	@Autowired
	private MarketService marketService;

	/**
	 * 시장 아이디에 따른 시장 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @throws Exception
	 * 
	 * @return Market
	 */
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public Market selectMarketById(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId) throws Exception {
		return marketService.selectMarketById(id, userId);
	}

	/**
	 * 위도, 경도에 따른 근처 가까운 시장 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param latitude
	 * @param longitude
	 * 
	 * @throws Exception
	 * 
	 * @return List<Market>
	 */
	@RequestMapping(value = "/nearness.json", method = RequestMethod.GET)
	public List<Market> selectMarketListByLocation(@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude) throws Exception {
		Location location = new Location(longitude, latitude);
		return marketService.selectMarketListByLocation(location);
	}

	/**
	 * 팔로우가 많은 순으로 시장 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @throws Exception
	 * 
	 * @return List<Market>
	 */
	@RequestMapping(value = "/sortByFollower.json", method = RequestMethod.GET)
	public List<Market> selectMarketListByFollower() throws Exception {
		return marketService.selectMarketListByFollower();
	}

	/**
	 * 키워드 검색에 따른 시장 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param keyword
	 * @param userId
	 * @param offset
	 * 
	 * @throws Exception
	 * 
	 * @return List<Market>
	 */
	@RequestMapping(value = "/search.json", method = RequestMethod.GET)
	public List<Market> selectMarketListByKeywordAndOffset(@RequestParam("keyword") String keyword,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return marketService.selectMarketListByKeywordAndOffset(keyword, userId, offset);
	}

	/**
	 * 시장아이디에 따른 해당 시장 상인 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * 
	 * @throws Exception
	 * 
	 * @return List<User>
	 */
	@RequestMapping(value = "/{id}/users.json", method = RequestMethod.GET)
	public List<User> selectUserListByIdAndOffset(@PathVariable long id, @RequestParam("offset") long offset,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId) throws Exception {
		return marketService.selectUserListByIdAndOffset(id, offset, userId);
	}

	/**
	 * 시장아이디에 따른 해당 시장의 스토리 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * 
	 * @throws Exception
	 * 
	 * @return List<Story>
	 */
	@RequestMapping(value = "/{id}/stories.json", method = RequestMethod.GET)
	public List<Story> selectStoryListByIdAndOffset(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return marketService.selectStoryListByIdAndUserIdAndOffset(id, userId, offset);
	}

	/**
	 * 시장아이디와 파일타입에 따른 해당 시장의 파일 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param type
	 * @param offset
	 * 
	 * @throws Exception
	 * 
	 * @return List<File>
	 */
	@RequestMapping(value = "/{id}/files/{type}.json", method = RequestMethod.GET)
	public List<File> selectFileListByIdAndTypeAndOffset(@PathVariable long id, @PathVariable int type,
			@RequestParam("offset") long offset) throws Exception {
		return marketService.selectFileListByIdAndTypeAndOffset(id, type, offset);
	}

	/**
	 * 시장별 시장 팔로우 생성 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param marketFollower
	 * 
	 * @throws Exception
	 * 
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}/follow", method = RequestMethod.POST)
	public void insertMarketFollower(@PathVariable long id, @RequestBody MarketFollower marketFollower)
			throws Exception {
		marketService.insertMarketFollower(marketFollower);
	}

	/**
	 * 시장별 시장 팔로우 삭제 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param marketFollower
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/{id}/follow", method = RequestMethod.DELETE)
	public void deleteMarketFollower(@PathVariable long id, @RequestBody MarketFollower marketFollower)
			throws Exception {
		marketService.deleteMarketFollower(marketFollower);
	}
}
