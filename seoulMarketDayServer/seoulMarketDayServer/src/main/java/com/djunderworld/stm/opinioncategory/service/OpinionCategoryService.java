package com.djunderworld.stm.opinioncategory.service;

import java.util.List;

import com.djunderworld.stm.common.dao.OpinionCategory;

/**
 * 유저 의견 카테고리 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface OpinionCategoryService {

	/**
	 * 유저 의견 카테고리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<OpinionCategory>
	 * @throws Exception
	 */
	List<OpinionCategory> selectOpinionCategoryList() throws Exception;

}
