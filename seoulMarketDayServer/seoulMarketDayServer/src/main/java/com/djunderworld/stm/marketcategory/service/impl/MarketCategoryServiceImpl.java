package com.djunderworld.stm.marketcategory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.MarketCategory;
import com.djunderworld.stm.marketcategory.service.MarketCategoryService;

/**
 * 시장카테고리 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MarketCategoryServiceImpl implements MarketCategoryService {

	@Autowired
	private MarketCategoryMapper marketCategoryMapper;

	/**
	 * 시장카테고리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<MarketCategory>
	 * @throws Exception
	 */
	@Override
	public List<MarketCategory> selectMarketCategoryList() throws Exception {
		return marketCategoryMapper.selectMarketCategoryList();
	}

}
