package com.djunderworld.stm.opinioncategory.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.OpinionCategory;
import com.djunderworld.stm.opinioncategory.service.OpinionCategoryService;

/**
 * 유저 의견 카테고리 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/opinioncategories")
public class OpinionCategoryController {

	@Autowired
	private OpinionCategoryService opinionCategoryService;

	/**
	 * 유저 의견 카테고리 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @throws Exception
	 * @return List<OpinionCategory>
	 * 
	 */
	@RequestMapping(value = "/all.json", method = RequestMethod.GET)
	@ResponseBody
	public List<OpinionCategory> selectOpinionCategoryList() throws Exception {
		return opinionCategoryService.selectOpinionCategoryList();
	}
}
