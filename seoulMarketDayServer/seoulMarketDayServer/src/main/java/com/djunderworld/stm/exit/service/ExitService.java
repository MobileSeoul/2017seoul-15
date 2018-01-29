package com.djunderworld.stm.exit.service;

import com.djunderworld.stm.common.dao.Exit;

/**
 * 유저 탈퇴 신청 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface ExitService {

	/**
	 * 유저 탈퇴 신청했는지의 여부에 따라 탈퇴신청 후 신청 존재 여부 반환 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param exit
	 * 
	 * @throws Exception
	 * 
	 * @return Boolean
	 */
	Boolean insertExit(Exit exit) throws Exception;

}
