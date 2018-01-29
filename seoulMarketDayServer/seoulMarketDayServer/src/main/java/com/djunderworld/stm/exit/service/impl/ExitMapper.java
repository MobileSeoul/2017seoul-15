package com.djunderworld.stm.exit.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Exit;

/**
 * 유저 탈퇴 신청 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface ExitMapper {

	/**
	 * 유저 탈퇴 신청 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param exit
	 * 
	 * @throws Exception
	 */
	void insertExit(Exit exit) throws Exception;

	/**
	 * 유저아이디에 따른 유저 탈퇴 신청 존재 여부 확인 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param userId
	 * 
	 * @return Boolean
	 * @throws Exception
	 */
	Boolean selectIsExistByUserId(@Param("userId") long userId) throws Exception;

}
