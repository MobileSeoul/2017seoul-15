package com.djunderworld.stm.report.service;

import com.djunderworld.stm.common.dao.Report;

/**
 * 컨텐츠 신고 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface ReportService {

	/**
	 * 컨텐츠 신고 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param report
	 * 
	 * @throws Exception
	 */
	void insertReport(Report report) throws Exception;

}
