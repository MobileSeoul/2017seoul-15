package com.djunderworld.stm.story.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.StoryComment;
import com.djunderworld.stm.common.dto.StoryDto;

/**
 * 스토리 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
public interface StoryService {

	/**
	 * 스토리 생성 함수
	 * 
	 * 스토리 내용, 해쉬태그, 파일정보(데이터베이스), 사진파일, 동영상파일, VR360파일(AWS S3) 등록
	 * 
	 * @author dongjooKim
	 * 
	 * @param story
	 * @param files
	 * @return long
	 * @throws Exception
	 */
	long insertStory(Story story, List<MultipartFile> files) throws Exception;

	/**
	 * 스토리 좋아요 생성 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @throws Exception
	 */
	void insertStoryLikeByIdAndUserId(long id, long userId) throws Exception;

	/**
	 * 스토리 좋아요 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @throws Exception
	 */
	void deleteStoryLikeByIdAndUserId(long id, long userId) throws Exception;

	/**
	 * 스토리 댓글 리스트 검색 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * @return List<StoryComment>
	 * @throws Exception
	 */
	List<StoryComment> selectStoryCommentListByIdAndUserIdAndOffset(long id, long userId, long offset) throws Exception;

	/**
	 * 스토리 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param story
	 * @throws Exception
	 */
	void deleteStory(Story story) throws Exception;

	/**
	 * 스토리 수정 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyDto
	 * @param files
	 * 
	 * @return Story
	 * @throws Exception
	 * 
	 */
	Story updateStory(StoryDto storyDto, List<MultipartFile> files) throws Exception;

	/**
	 * 스토리 검색 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @return Story
	 * @throws Exception
	 * 
	 */
	Story selectStoryByIdAndUserId(long id, long userId) throws Exception;

	/**
	 * 월별 좋아요 많고 사진있는 best5 스토리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	List<Story> selectBestStoryListPerMonthLimitFive() throws Exception;

}
