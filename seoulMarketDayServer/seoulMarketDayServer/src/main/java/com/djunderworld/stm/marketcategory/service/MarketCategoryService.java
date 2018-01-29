package com.djunderworld.stm.marketcategory.service;

import java.util.List;

import com.djunderworld.stm.common.dao.MarketCategory;
import com.djunderworld.stm.common.dao.RegionCategory;

/**
 * 시장카테고리 관련 서비스레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface MarketCategoryService {

	/**
	 * 시장카테고리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<MarketCategory>
	 * @throws Exception
	 */
	List<MarketCategory> selectMarketCategoryList() throws Exception;

}
