package com.djunderworld.stm.regioncategory.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.RegionCategory;

/**
 * 지역구 관련 레파지토리레이어 인터페이스
 * 
 * @author dongjooKim
 */
@Repository
public interface RegionCategoryMapper {

	/**
	 * 지역구 id를 기준으로 지역구 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @return RegionCategory
	 * @throws Exception
	 */
	RegionCategory selectRegionCategoryById(@Param("id") long id) throws Exception;

	/**
	 * 지역구 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<RegionCategory>
	 * @throws Exception
	 */
	List<RegionCategory> selectRegionCategoryList() throws Exception;

}
