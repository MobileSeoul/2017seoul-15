package com.djunderworld.stm.report.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.Report;
import com.djunderworld.stm.report.service.ReportService;

/**
 * 컨텐츠 신고 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
		
	
	/**
	 * 컨텐츠 신고 생성 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param report
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public void insertReport(@RequestBody Report report) throws Exception {
		reportService.insertReport(report);
	}
}
