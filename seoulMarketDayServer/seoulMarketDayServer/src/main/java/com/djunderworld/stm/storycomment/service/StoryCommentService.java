package com.djunderworld.stm.storycomment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.common.dao.StoryComment;

/**
 * 스토리댓글 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface StoryCommentService {

	/**
	 * 스토리 댓글 및 파일 생성(데이터베이스 & S3) 함수 
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * @param files
	 * @return StoryComment
	 * 
	 * @throws Exception
	 */
	StoryComment insertStoryComment(StoryComment storyComment, List<MultipartFile> files) throws Exception;

	/**
	 * 스토리 댓글 답변 검색 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param groupId
	 * @param userId
	 * @param offset
	 * @return List<StoryComment>
	 * 
	 * @throws Exception
	 */
	List<StoryComment> selectStoryCommentReplyListByGroupIdAndUserIdAndOffset(long groupId, long userId, long offset)
			throws Exception;

	/**
	 * 스토리 댓글 삭제함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * 
	 * @throws Exception
	 */
	void deleteStoryComment(StoryComment storyComment) throws Exception;

	/**
	 * 스토리 댓글 수정 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * 
	 * @throws Exception
	 */
	void updateStoryComment(StoryComment storyComment) throws Exception;

}
