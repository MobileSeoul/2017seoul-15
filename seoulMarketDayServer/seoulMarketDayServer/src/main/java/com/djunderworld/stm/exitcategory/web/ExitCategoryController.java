package com.djunderworld.stm.exitcategory.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.ExitCategory;
import com.djunderworld.stm.exitcategory.service.ExitCategoryService;

/**
 * 유저 탈퇴 사유 카테고리 관련 컨트롤러
 * 
 * @author dongjooKim
 * 
 */
@Controller
@RequestMapping("/exitcategories")
public class ExitCategoryController {

	@Autowired
	private ExitCategoryService exitCategoryService;

	/**
	 * 유저 탈퇴 사유 전체 카테고리 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<ExitCategory>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/all.json", method = RequestMethod.GET)
	@ResponseBody
	public List<ExitCategory> selectExitCategoryList() throws Exception {
		return exitCategoryService.selectExitCategoryList();
	}
}
