package com.djunderworld.stm.exitcategory.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.ExitCategory;

/**
 * 유저 탈퇴 사유 카테고리 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface ExitCategoryMapper {

	/**
	 * 유저 탈퇴 사유 전체 카테고리 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<ExitCategory>
	 * 
	 * @throws Exception
	 */
	List<ExitCategory> selectExitCategoryList() throws Exception;

}
