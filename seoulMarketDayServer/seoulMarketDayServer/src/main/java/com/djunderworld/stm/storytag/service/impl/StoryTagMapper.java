package com.djunderworld.stm.storytag.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.StoryTag;

/**
 * 스토리 태그 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 */
@Repository
public interface StoryTagMapper {

	/**
	 * 스토리 아이디로 스토리 태그 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyId
	 * 
	 * @throws Exception
	 */
	void deleteStoryTagByStoryId(@Param("storyId") long storyId) throws Exception;

	/**
	 * 스토리 태그 리스트 생성 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyTags
	 * 
	 * @throws Exception
	 */
	void insertStoryTags(@Param("storyTags") List<StoryTag> storyTags) throws Exception;

}
