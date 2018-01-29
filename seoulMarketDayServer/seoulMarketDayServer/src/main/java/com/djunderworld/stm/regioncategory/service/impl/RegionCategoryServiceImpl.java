package com.djunderworld.stm.regioncategory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.RegionCategory;
import com.djunderworld.stm.market.service.impl.MarketMapper;
import com.djunderworld.stm.regioncategory.service.RegionCategoryService;

/**
 * 지역구 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RegionCategoryServiceImpl implements RegionCategoryService {

	@Autowired
	private RegionCategoryMapper regionCategoryMapper;

	@Autowired
	private MarketMapper marketMapper;

	/**
	 * 지역구 id를 기준으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param marketCategoryId
	 * @return List<Market>
	 * @throws Exception
	 */
	@Override
	public List<Market> selectMarketListById(long id, long marketCategoryId) throws Exception {
		return marketMapper.selectMarketListByRegionCategoryId(id, marketCategoryId);
	}

	/**
	 * 지역구 id를 기준으로 지역구 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @return RegionCategory
	 * @throws Exception
	 */
	@Override
	public RegionCategory selectRegionCategoryById(long id) throws Exception {
		return regionCategoryMapper.selectRegionCategoryById(id);
	}

	/**
	 * 지역구 id 및 위도,경도를 기준으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param latitude
	 * @param longitude
	 * @return List<Market>
	 * @throws Exception
	 */
	@Override
	public List<Market> selectMarketListByIdAndLocation(long id, double latitude, double longitude) throws Exception {
		return marketMapper.selectMarketListByRegionCategoryIdAndLocation(id, latitude, longitude);
	}

	/**
	 * 지역구 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<RegionCategory>
	 * @throws Exception
	 */
	@Override
	public List<RegionCategory> selectRegionCategoryList() throws Exception {
		return regionCategoryMapper.selectRegionCategoryList();
	}

}
