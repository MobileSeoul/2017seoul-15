package com.djunderworld.stm.opinioncategory.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.OpinionCategory;

/**
 * 유저 의견 카테고리 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface OpinionCategoryMapper {

	/**
	 * 유저 의견 카테고리 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<OpinionCategory>
	 * @throws Exception
	 */
	List<OpinionCategory> selectOpinionCategoryList() throws Exception;

}
