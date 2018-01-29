package com.djunderworld.stm.story.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.Story;

/**
 * 스토리 관련 레파지토리레이어 인터페이스
 * 
 * @author dongjooKim
 */
@Repository
public interface StoryMapper {

	/**
	 * 스토리 생성 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param story
	 * @return long
	 * @throws Exception
	 */
	long insertStory(Story story) throws Exception;

	/**
	 * 스토리 유저 아이디로 스토리 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyUserId
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	List<Story> selectStoryListByStoryUserIdAndUserIdAndOffset(@Param("storyUserId") long storyUserId,
			@Param("userId") long userId, @Param("offset") long offset);

	/**
	 * 스토리 아이디와 유저아이디로 스토리 좋아요 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @throws Exception
	 */
	void insertStoryLikeByIdAndUserId(@Param("id") long id, @Param("userId") long userId) throws Exception;

	/**
	 * 시장 아이디로 스토리 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketId
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	List<Story> selectStoryListByMarketIdAndUserIdAndOffset(@Param("marketId") long marketId,
			@Param("userId") long userId, @Param("offset") long offset) throws Exception;

	/**
	 * 스토리 해쉬태그 이름로 스토리 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param tagName
	 * @param userId
	 * @param offset
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	List<Story> selectStoryListByTagName(@Param("tagName") String tagName, @Param("userId") long userId,
			@Param("offset") long offset) throws Exception;

	/**
	 * 스토리 아이디로 스토리 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @return Story
	 * @throws Exception
	 */
	Story selectStoryByIdAndUserId(@Param("id") long id, @Param("userId") long userId) throws Exception;

	/**
	 * 스토리 수정 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param story
	 * 
	 * @throws Exception
	 */
	void updateStory(Story story) throws Exception;

	/**
	 * 스토리 좋아요 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @throws Exception
	 */
	void deleteStoryLikeByIdAndUserId(@Param("id") long id, @Param("userId") long userId) throws Exception;

	/**
	 * 스토리 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * @throws Exception
	 */
	void deleteStoryById(@Param("id") long id) throws Exception;

	/**
	 * 이번달 좋아요가 가장많은 순으로 스토리 리스트 5개 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	List<Story> selectBestStoryListPerMonthLimitFive() throws Exception;

}
