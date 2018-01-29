package com.djunderworld.stm.exit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Exit;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.exit.service.ExitService;

/**
 * 유저 탈퇴 신청 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExitServiceImpl implements ExitService {

	@Autowired
	private ExitMapper exitMapper;

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
	@Override
	public Boolean insertExit(Exit exit) throws Exception {
		User user = exit.getUser();
		long userId = user.getId();

		Boolean isExist = exitMapper.selectIsExistByUserId(userId);
		if (!isExist) {
			exitMapper.insertExit(exit);
		}
		
		return isExist;
	}

}
