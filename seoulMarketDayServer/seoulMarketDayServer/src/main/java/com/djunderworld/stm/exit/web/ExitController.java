package com.djunderworld.stm.exit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.Exit;
import com.djunderworld.stm.exit.service.ExitService;



/**
 * 유저 탈퇴 신청 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/exits")
public class ExitController {

	@Autowired
	private ExitService exitService;

	/**
	 * 유저 탈퇴 신청했는지의 여부에 따라 탈퇴신청 후 신청 존재 여부 반환 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param exit
	 * 
	 * @throws Exception
	 * 
	 * @return Boolean
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public Boolean insertExit(@RequestBody Exit exit) throws Exception {
		return exitService.insertExit(exit);
	}
	
}
