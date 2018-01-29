package com.djunderworld.stm.storycomment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.StoryComment;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.flag.FileFlag;
import com.djunderworld.stm.common.utils.AwsS3Util;
import com.djunderworld.stm.file.service.impl.FileMapper;
import com.djunderworld.stm.storycomment.service.StoryCommentService;

/**
 * 스토리댓글 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StoryCommentServiceImpl implements StoryCommentService {
	@Autowired
	private AwsS3Util awsS3Util;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private StoryCommentMapper storyCommentMapper;

	@Value("#{aws['aws.s3.comment.image.url']}")
	private String COMMENT_IMAGE_URL;

	@Value("#{aws['aws.s3.comment.video.url']}")
	private String COMMENT_VIDEO_URL;

	@Value("#{aws['aws.s3.comment.vr360.url']}")
	private String COMMENT_VR360_URL;

	/**
	 * 스토리 댓글 및 파일 생성(데이터베이스 & S3) 함수 
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * @param multipartFiles
	 * @return StoryComment
	 * 
	 * @throws Exception
	 */
	@Override
	public StoryComment insertStoryComment(StoryComment storyComment, List<MultipartFile> multipartFiles)
			throws Exception {
		long depth = storyComment.getDepth();

		if (depth == 0) {
			long newGroupId = storyCommentMapper.selectMaxGroupId() + 1;
			storyComment.setGroupId(newGroupId);

			storyCommentMapper.insertComment(storyComment);
		} else {
			long groupId = storyComment.getGroupId();
			long newPosition = storyCommentMapper.selectMaxPositionByGroupId(groupId) + 1;
			storyComment.setPosition(newPosition);

			storyCommentMapper.insertComment(storyComment);
		}

		int multipartFileSize = multipartFiles.size();

		if (multipartFileSize > 0) {
			for (int i = 0; i < multipartFileSize; i++) {
				Story story = storyComment.getStory();
				User user = storyComment.getUser();

				File file = new File();
				file.setStory(story);
				file.setUser(user);
				file.setPostCategoryId(FileFlag.COMMENT_POST);

				MultipartFile multipartFile = multipartFiles.get(i);

				String fileType = multipartFile.getContentType().trim();
				String orgname = multipartFile.getOriginalFilename();

				long fileSize = multipartFile.getSize();
				file.setSize(fileSize);

				String fileName = null;

				if (fileType.equals(FileFlag.PHOTO_CONTENT_TYPE)) {
					file.setType(FileFlag.PHOTO_TYPE);

					String ext = orgname.substring(orgname.lastIndexOf(".") + 1, orgname.length());
					file.setExt(ext);

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(COMMENT_IMAGE_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(COMMENT_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}

				if (fileType.equals(FileFlag.VR360_CONTENT_TYPE)) {
					String ext = orgname.substring(orgname.lastIndexOf(".") + 1, orgname.length());
					file.setExt(ext);
					file.setType(FileFlag.VR360_TYPE);

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(COMMENT_VR360_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_THUMBNAIL_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_THUMBNAIL_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_THUMBNAIL_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(COMMENT_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}
				file.setName(fileName);

				fileMapper.insertFile(file);

				long storyCommentId = storyComment.getId();
				long fileId = file.getId();
				storyCommentMapper.insertCommentFile(storyCommentId, fileId);

				if (!fileType.equals(FileFlag.VIDEO_CONTENT_TYPE)) {
					storyComment.setFile(file);
				}
			}
		}

		return storyComment;
	}

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
	@Override
	public List<StoryComment> selectStoryCommentReplyListByGroupIdAndUserIdAndOffset(long groupId, long userId,
			long offset) throws Exception {
		return storyCommentMapper.selectStoryCommentReplyListByGroupIdAndUserIdAndOffset(groupId, userId, offset);
	}

	/**
	 * 스토리 댓글 삭제함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * @throws Exception
	 */
	@Override
	public void deleteStoryComment(StoryComment storyComment) throws Exception {
		long id = storyComment.getId();
		long depth = storyComment.getDepth();
		long groupId = storyComment.getGroupId();

		if (depth == 0) {
			List<File> files = fileMapper.selectFileListByGroupId(groupId);

			storyCommentMapper.deleteStoryCommentByGroupId(groupId);

			int fileSize = files.size();
			
			if (fileSize > 0) {
				fileMapper.deleteFiles(files);
			}
			
			for (File file : files) {
				int type = file.getType();
				String fileName = file.getName();
				String ext = file.getExt();

				if (type == FileFlag.PHOTO_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_IMAGE_URL, fileName, ext);
				} else if (type == FileFlag.VR360_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_VR360_URL, fileName, ext);
				} else {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_VIDEO_URL, fileName, ext);
				}
			}

		} else {
			storyCommentMapper.deleteStoryCommentById(id);
			File file = storyComment.getFile();

			if (file != null) {
				long fileId = file.getId();
				fileMapper.deleteFileById(fileId);

				int type = file.getType();
				String fileName = file.getName();
				String ext = file.getExt();

				if (type == FileFlag.PHOTO_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_IMAGE_URL, fileName, ext);
				} else if (type == FileFlag.VR360_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_VR360_URL, fileName, ext);
				} else {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(COMMENT_VIDEO_URL, fileName, ext);
				}
			}
		}

	}

	/**
	 * 스토리 댓글 수정 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * @throws Exception
	 */
	@Override
	public void updateStoryComment(StoryComment storyComment) throws Exception {
		storyCommentMapper.updateStoryComment(storyComment);
	}

}
