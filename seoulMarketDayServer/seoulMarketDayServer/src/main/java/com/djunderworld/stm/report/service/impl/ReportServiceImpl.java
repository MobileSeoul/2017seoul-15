package com.djunderworld.stm.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Report;
import com.djunderworld.stm.report.service.ReportService;

/**
 * 컨텐츠 신고 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ReportMapper reportMapper;

	/**
	 * 컨텐츠 신고 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param report
	 * 
	 * @throws Exception
	 */
	@Override
	public void insertReport(Report report) throws Exception {
		reportMapper.insertReport(report);
	}

}
