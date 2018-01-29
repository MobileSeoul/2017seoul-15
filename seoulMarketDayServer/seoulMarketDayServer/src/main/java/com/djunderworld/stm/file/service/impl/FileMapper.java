package com.djunderworld.stm.file.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.File;

/**
 * 파일 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface FileMapper {

	/**
	 * 파일 리스트 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param files
	 * 
	 * @throws Exception
	 * 
	 */
	void insertFiles(@Param("files") List<File> files) throws Exception;

	/**
	 * 유저 아이디로 유저가 업로드한 타입별 파일 리스트를 오프셋에 따라 검색 매핑하는 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param userId
	 * @param type
	 * @param offset
	 * 
	 * @throws Exception
	 * @return List<File>
	 */
	List<File> selectFileListByUserIdAndTypeAndOffset(@Param("userId") long userId, @Param("type") int type,
			@Param("offset") long offset) throws Exception;

	/**
	 * 시장 아이디로 상인들이 업로드한 타입별 파일 리스트를 오프셋에 따라 검색 매핑하는 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param marketId
	 * @param type
	 * @param offset
	 * 
	 * @throws Exception
	 * @return List<File>
	 */
	List<File> selectFileListByMarketIdAndTypeAndOffset(@Param("marketId") long marketId, @Param("type") int type,
			@Param("offset") long offset) throws Exception;

	/**
	 * 파일 조회수 증가 업데이트 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * @throws Exception
	 * 
	 */
	void updateFileByHits(@Param("id") long id) throws Exception;

	/**
	 * 파일 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param file
	 * 
	 * @throws Exception
	 * @return long
	 */
	long insertFile(File file) throws Exception;

	/**
	 * 댓글 그룹아이디에 따른 파일 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param groupId
	 * 
	 * @throws Exception
	 * @return List<File>
	 */
	List<File> selectFileListByGroupId(@Param("groupId") long groupId) throws Exception;

	/**
	 * 파일 리스트 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param files
	 * 
	 * @throws Exception
	 */
	void deleteFiles(@Param("files") List<File> files) throws Exception;

	/**
	 * 댓글 아이디로 파일 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param commentId
	 * 
	 * @throws Exception
	 * @return File
	 */
	File selectFileByCommentId(@Param("commentId") long commentId) throws Exception;

	/**
	 * 파일 아이디로 파일 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * 
	 * @throws Exception
	 */
	void deleteFileById(@Param("id") long id) throws Exception;

	/**
	 * 스토리 아이디에 따른 댓글에 업로드 된 파일 리스트 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyId
	 * 
	 * @throws Exception
	 * @return List<File>
	 */
	List<File> selectCommentFileListByStoryId(@Param("storyId") long storyId) throws Exception;

}
