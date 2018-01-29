package com.djunderworld.stm.opinion.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.Opinion;
import com.djunderworld.stm.opinion.service.OpinionService;

/**
 * 유저 의견 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/opinions")
public class OpinionController {

	@Autowired
	private OpinionService opinionService;

	/**
	 * 유저 의견 생성 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param opinion
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public void insertOpinion(@RequestBody Opinion opinion) throws Exception {
		opinionService.insertOpinion(opinion);
	}
}
