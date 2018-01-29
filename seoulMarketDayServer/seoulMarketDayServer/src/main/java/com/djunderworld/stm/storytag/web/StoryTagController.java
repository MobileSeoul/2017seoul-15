package com.djunderworld.stm.storytag.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.storytag.service.StoryTagService;

/**
 * 스토리 태그 관련 컨트롤러
 * 
 * @author dongjooKim
 */
@Controller
@RequestMapping("/storytags")
public class StoryTagController {

	@Autowired
	private StoryTagService storyTagService;

	/**
	 * 스토리 태그 별 스토리 리스트 검색 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param tagName
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Story>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/stories.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Story> selectStoryListByTagName(@RequestParam String tagName,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return storyTagService.selectStoryListByTagName(tagName, userId, offset);
	}
}
