package com.djunderworld.stm.behavior.service;

import com.djunderworld.stm.common.dao.Behavior;

/**
 * 유저행위 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface BehaviorService {

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
	Behavior selectBehaviorById(long id) throws Exception;

}
