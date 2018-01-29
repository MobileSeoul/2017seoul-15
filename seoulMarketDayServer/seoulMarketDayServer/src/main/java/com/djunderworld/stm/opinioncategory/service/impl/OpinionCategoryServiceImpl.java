package com.djunderworld.stm.opinioncategory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.OpinionCategory;
import com.djunderworld.stm.opinioncategory.service.OpinionCategoryService;

/**
 * 유저 의견 카테고리 관련 서비스 레이어 인터페이스 구현 클래스 
 * 
 * @author dongjooKim
 * 
 */
@Service 
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OpinionCategoryServiceImpl implements OpinionCategoryService{
	
	@Autowired
	private OpinionCategoryMapper opinionCategoryMapper;

	
	/**
	 * 유저 의견 카테고리 리스트 검색 함수 
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<OpinionCategory>
	 * @throws Exception
	 */
	@Override
	public List<OpinionCategory> selectOpinionCategoryList() throws Exception {
		return opinionCategoryMapper.selectOpinionCategoryList();
	}
}
