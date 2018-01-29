package com.djunderworld.stm.marketfollower.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.marketfollower.service.MarketFollowerService;

/**
 * 시장 팔로우 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MarketFollowerServiceImpl implements MarketFollowerService {

	@Autowired
	private MarketFollowerMapper marketFollowerMapper;

}
