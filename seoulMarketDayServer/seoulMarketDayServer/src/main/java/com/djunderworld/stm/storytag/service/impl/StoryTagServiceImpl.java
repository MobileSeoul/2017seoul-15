package com.djunderworld.stm.storytag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.story.service.impl.StoryMapper;
import com.djunderworld.stm.storytag.service.StoryTagService;

/** 
 * 스토리 태그 관련 서비스 레이어 인터페이스  구현 클래스
 * 
 * @author dongjooKim
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StoryTagServiceImpl implements StoryTagService {

	@Autowired
	private StoryMapper storyMapper;

	/**
	 * 스토리 태그 별 스토리 리스트 검색 함수
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
	@Override
	public List<Story> selectStoryListByTagName(String tagName, long userId, long offset) throws Exception {
		return storyMapper.selectStoryListByTagName(tagName, userId, offset);
	}

}
