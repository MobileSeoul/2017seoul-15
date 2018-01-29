package com.djunderworld.stm.marketuser.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.marketuser.service.MarketUserService;

/**
 * 시장 상인 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MarketUserServiceImpl implements MarketUserService {

}
