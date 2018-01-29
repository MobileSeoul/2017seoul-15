package com.djunderworld.stm.storycomment.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.StoryComment;

/**
 * 스토리댓글 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 */
@Repository
public interface StoryCommentMapper {

	/**
	 * 스토리아이디 별 스토리 댓글 리스트 검색 매핑 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyId
	 * @param userId
	 * @param offset
	 * 
	 * @return List<StoryComment>
	 * 
	 * @throws Exception
	 */
	List<StoryComment> selectStoryCommentListByStoryIdAndUserIdAndOffset(@Param("storyId") long storyId,
			@Param("userId") long userId, @Param("offset") long offset) throws Exception;

	/**
	 * 스토리 댓글 그룹 아이디 최대값 검색 매핑 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @return long
	 * 
	 * @throws Exception
	 */
	long selectMaxGroupId() throws Exception;

	/**
	 * 스토리 댓글 포지션 최대값 검색 매핑 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param groupId
	 * 
	 * @return long
	 * 
	 * @throws Exception
	 */
	long selectMaxPositionByGroupId(@Param("groupId") long groupId) throws Exception;

	/**
	 * 스토리 댓글 생성 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * 
	 * @return long
	 * 
	 * @throws Exception
	 */
	long insertComment(StoryComment storyComment) throws Exception;

	/**
	 * 스토리 파일 연관관계 생성 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyCommentId
	 * @param fileId
	 * 
	 * 
	 * @throws Exception
	 */
	void insertCommentFile(@Param("storyCommentId") long storyCommentId, @Param("fileId") long fileId) throws Exception;

	/**
	 * 댓글 그룹아이디 별 스토리 댓글 답변 리스트 검색 매핑 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param groupId
	 * @param userId
	 * @param offset
	 * 
	 * @return List<StoryComment>
	 * 
	 * @throws Exception
	 */
	List<StoryComment> selectStoryCommentReplyListByGroupIdAndUserIdAndOffset(@Param("groupId") long groupId,
			@Param("userId") long userId, @Param("offset") long offset) throws Exception;

	/**
	 * 스토리 댓글 그룹아이디 별  스토리 댓글 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param groupId
	 * 
	 * 
	 * @throws Exception
	 */
	void deleteStoryCommentByGroupId(@Param("groupId") long groupId) throws Exception;

	/**
	 * 스토리 댓글 아이디 별 스토리 댓글 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * 
	 * @throws Exception
	 */
	void deleteStoryCommentById(@Param("id") long id) throws Exception;

	/**
	 * 스토리 댓글 수정 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * 
	 * 
	 * @throws Exception
	 */
	void updateStoryComment(StoryComment storyComment) throws Exception;

}
