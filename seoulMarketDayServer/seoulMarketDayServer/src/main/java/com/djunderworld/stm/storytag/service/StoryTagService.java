package com.djunderworld.stm.storytag.service;

import java.util.List;

import com.djunderworld.stm.common.dao.Story;

/**
 * 스토리 태그 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface StoryTagService {

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
	List<Story> selectStoryListByTagName(String tagName, long userId, long offset) throws Exception;

}
