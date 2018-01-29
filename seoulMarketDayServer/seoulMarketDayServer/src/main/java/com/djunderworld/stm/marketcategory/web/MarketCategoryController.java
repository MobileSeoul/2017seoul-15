package com.djunderworld.stm.marketcategory.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.djunderworld.stm.common.dao.MarketCategory;
import com.djunderworld.stm.marketcategory.service.MarketCategoryService;

/**
 * 시장카테고리 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@RestController
@RequestMapping("/marketcategories")
public class MarketCategoryController {
	@Autowired
	private MarketCategoryService marketCategoryService;

	/**
	 * 시장 카테고리 전체 리스트 검색 API
	 * 
	 * @author dongjooKimt
	 * 
	 * @throws Exception
	 * 
	 * @return List<MarketCategory>
	 */
	@RequestMapping(value = "/all.json", method = RequestMethod.GET)
	public List<MarketCategory> selectMarketCategoryList() throws Exception {
		return marketCategoryService.selectMarketCategoryList();
	}
}
