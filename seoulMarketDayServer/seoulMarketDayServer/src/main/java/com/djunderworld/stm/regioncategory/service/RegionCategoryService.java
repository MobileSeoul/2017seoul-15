package com.djunderworld.stm.regioncategory.service;

import java.util.List;

import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.RegionCategory;

/**
 * 지역구 관련 서비스레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface RegionCategoryService {

	/**
	 * 지역구 id를 기준으로 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param marketCategoryId
	 * @return List<Market>
	 * @throws Exception
	 */
	List<Market> selectMarketListById(long id, long marketCategoryId) throws Exception;

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
	RegionCategory selectRegionCategoryById(long id) throws Exception;

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
	List<Market> selectMarketListByIdAndLocation(long id, double latitude, double longitude) throws Exception;

	/**
	 * 지역구 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<RegionCategory>
	 * @throws Exception
	 */
	List<RegionCategory> selectRegionCategoryList() throws Exception;

}
