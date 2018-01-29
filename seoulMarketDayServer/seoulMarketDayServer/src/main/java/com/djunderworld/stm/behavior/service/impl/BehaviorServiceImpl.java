package com.djunderworld.stm.behavior.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djunderworld.stm.behavior.service.BehaviorService;
import com.djunderworld.stm.common.dao.Behavior;

/**
 * 유저행위 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
public class BehaviorServiceImpl implements BehaviorService {
	@Autowired
	private BehaviorMapper behaviorMapper;

	/**
	 * 유저 행위 검색함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @return Behavior
	 * 
	 * @throws Exception
	 */
	@Override
	public Behavior selectBehaviorById(long id) throws Exception {
		return behaviorMapper.selectBehaviorById(id);
	}

}
