package com.djunderworld.stm.marketcategory.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.MarketCategory;

/**
 * 시장카테고리 관련 레파지토리레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface MarketCategoryMapper {

	/**
	 * 시장카테고리 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<MarketCategory>
	 * @throws Exception
	 */
	List<MarketCategory> selectMarketCategoryList() throws Exception;

}
