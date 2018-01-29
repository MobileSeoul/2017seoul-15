package com.djunderworld.stm.regioncategory.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.RegionCategory;
import com.djunderworld.stm.regioncategory.service.RegionCategoryService;

/**
 * 지역구 카테고리 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@RestController
@RequestMapping("/regioncategories")
public class RegionCategoryController {

	@Autowired
	private RegionCategoryService regionCategoryService;

	/**
	 * 지역구 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<RegionCategory>
	 * @throws Exception
	 */
	@RequestMapping(value = "/all.json", method = RequestMethod.GET)
	public List<RegionCategory> selectRegionCategoryList() throws Exception {
		return regionCategoryService.selectRegionCategoryList();
	}

	/**
	 * 지역구 id를 기준으로 시장 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param marketCategoryId
	 * @return List<Market>
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/markets.json", method = RequestMethod.GET)
	public List<Market> selectMarketListById(@PathVariable long id,
			@RequestParam(name = "marketCategoryId", defaultValue = "0") long marketCategoryId) throws Exception {
		System.out.println(marketCategoryId);
		return regionCategoryService.selectMarketListById(id, marketCategoryId);
	}

	/**
	 * 지역구 id 및 위도,경도를 기준으로 시장 리스트 검색 API
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
	@RequestMapping(value = "/{id}/markets/nearness.json", method = RequestMethod.GET)
	public List<Market> selectMarketListByIdAndLocation(@PathVariable long id,
			@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) throws Exception {
		return regionCategoryService.selectMarketListByIdAndLocation(id, latitude, longitude);
	}

	/**
	 * 지역구 id를 기준으로 지역구 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @return RegionCategory
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public RegionCategory selectRegionCategoryById(@PathVariable long id) throws Exception {
		return regionCategoryService.selectRegionCategoryById(id);
	}

}
