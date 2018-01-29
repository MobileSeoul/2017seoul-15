package com.djunderworld.stm.behavior.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Behavior;

/**
 * 유저행위 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface BehaviorMapper {

	/**
	 * 유저 행위 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @return Behavior
	 * 
	 * @throws Exception
	 */
	Behavior selectBehaviorById(@Param("id") long id) throws Exception;
}
