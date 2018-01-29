package com.djunderworld.stm.merchantfollower.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.merchantfollower.service.MerchantFollowerService;

/**
 * 상인 팔로우 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MerchantFollowerServiceImpl implements MerchantFollowerService {

	@Autowired
	private MerchantFollowerMapper merchantFollowerMapper;

}
