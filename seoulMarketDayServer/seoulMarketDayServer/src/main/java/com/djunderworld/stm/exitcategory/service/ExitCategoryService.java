package com.djunderworld.stm.exitcategory.service;

import java.util.List;

import com.djunderworld.stm.common.dao.ExitCategory;

/**
 * 유저 탈퇴 사유 카테고리 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface ExitCategoryService {

	/**
	 * 유저 탈퇴 사유 전체 카테고리 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<ExitCategory>
	 * 
	 * @throws Exception
	 */
	List<ExitCategory> selectExitCategoryList() throws Exception;

}
