package com.djunderworld.stm.report.service.impl;

import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Report;

/**
 * 컨텐츠 신고 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface ReportMapper {

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
