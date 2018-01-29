package com.djunderworld.stm.exitcategory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.ExitCategory;
import com.djunderworld.stm.exitcategory.service.ExitCategoryService;

/**
 * 유저 탈퇴 사유 카테고리 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExitCategoryServiceImpl implements ExitCategoryService{

	@Autowired
	private ExitCategoryMapper exitCategoryMapper;

	
	/**
	 * 유저 탈퇴 사유 전체 카테고리 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<ExitCategory>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<ExitCategory> selectExitCategoryList() throws Exception {
		return exitCategoryMapper.selectExitCategoryList();
	}
}
