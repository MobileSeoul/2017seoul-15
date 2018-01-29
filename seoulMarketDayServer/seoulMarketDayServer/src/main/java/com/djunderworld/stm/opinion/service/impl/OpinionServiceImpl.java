package com.djunderworld.stm.opinion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Opinion;
import com.djunderworld.stm.opinion.service.OpinionService;

/**
 * 유저 의견 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OpinionServiceImpl implements OpinionService {

	@Autowired
	private OpinionMapper opinionMapper;

	/**
	 * 유저 의견 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param opinion
	 * @throws Exception
	 */
	@Override
	public void insertOpinion(Opinion opinion) throws Exception {
		opinionMapper.insertOpinion(opinion);
	}
}
