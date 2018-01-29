package com.djunderworld.stm.story.service.impl;

import java.util.ArrayList;
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
import com.djunderworld.stm.common.dao.StoryTag;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.StoryDto;
import com.djunderworld.stm.common.flag.FileFlag;
import com.djunderworld.stm.common.utils.AwsS3Util;
import com.djunderworld.stm.file.service.impl.FileMapper;
import com.djunderworld.stm.firebasenotification.service.impl.FirebaseNotificationMapper;
import com.djunderworld.stm.story.service.StoryService;
import com.djunderworld.stm.storycomment.service.impl.StoryCommentMapper;
import com.djunderworld.stm.storytag.service.impl.StoryTagMapper;

/**
 * 스토리 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryMapper storyMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private StoryCommentMapper storyCommentMapper;

	@Autowired
	private FirebaseNotificationMapper firebaseNotificationMapper;

	@Autowired
	private StoryTagMapper storyTagMapper;

	@Autowired
	private AwsS3Util awsS3Util;

	@Value("#{aws['aws.s3.story.image.url']}")
	private String STORY_IMAGE_URL;

	@Value("#{aws['aws.s3.story.video.url']}")
	private String STORY_VIDEO_URL;

	@Value("#{aws['aws.s3.story.vr360.url']}")
	private String STORY_VR360_URL;

	@Value("#{aws['aws.s3.comment.image.url']}")
	private String COMMENT_IMAGE_URL;

	@Value("#{aws['aws.s3.comment.video.url']}")
	private String COMMENT_VIDEO_URL;

	@Value("#{aws['aws.s3.comment.vr360.url']}")
	private String COMMENT_VR360_URL;

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
	@Override
	public long insertStory(Story story, List<MultipartFile> multipartFiles) throws Exception {
		storyMapper.insertStory(story);
		long storyId = story.getId();

		int multipartFileSize = multipartFiles.size();

		if (multipartFileSize > 0) {
			List<File> files = new ArrayList<File>();

			for (int i = 0; i < multipartFileSize; i++) {
				File file = new File();
				file.setStory(story);

				User storyUser = story.getUser();
				file.setUser(storyUser);
				file.setPostCategoryId(FileFlag.STORY_POST);

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

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(STORY_IMAGE_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(STORY_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}

				if (fileType.equals(FileFlag.VR360_CONTENT_TYPE)) {
					String ext = orgname.substring(orgname.lastIndexOf(".") + 1, orgname.length());
					file.setExt(ext);
					file.setType(FileFlag.VR360_TYPE);

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(STORY_VR360_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_THUMBNAIL_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_THUMBNAIL_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_THUMBNAIL_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(STORY_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}

				file.setName(fileName);
				files.add(file);
			}

			fileMapper.insertFiles(files);
		}

		List<StoryTag> storyTags = story.getStoryTags();
		int storyTagSize = storyTags.size();

		if (storyTagSize > 0) {

			for (int i = 0; i < storyTagSize; i++) {
				storyTags.get(i).setStory(story);
			}
			storyTagMapper.insertStoryTags(storyTags);
		}

		return storyId;
	}

	/**
	 * 스토리 좋아요 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void insertStoryLikeByIdAndUserId(long id, long userId) throws Exception {
		storyMapper.insertStoryLikeByIdAndUserId(id, userId);
	}

	/**
	 * 스토리 좋아요 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void deleteStoryLikeByIdAndUserId(long id, long userId) throws Exception {
		storyMapper.deleteStoryLikeByIdAndUserId(id, userId);
		firebaseNotificationMapper.deleteFirebaseNotificationRegardingStoryLikeByStoryIdAndUserId(id, userId);
	}

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
	@Override
	public List<StoryComment> selectStoryCommentListByIdAndUserIdAndOffset(long id, long userId, long offset)
			throws Exception {
		return storyCommentMapper.selectStoryCommentListByStoryIdAndUserIdAndOffset(id, userId, offset);
	}

	/**
	 * 스토리 삭제 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param story
	 * @throws Exception
	 */
	@Override
	public void deleteStory(Story story) throws Exception {
		long storyId = story.getId();
		List<File> storyFiles = story.getFiles();
		List<File> commentFiles = fileMapper.selectCommentFileListByStoryId(storyId);

		storyMapper.deleteStoryById(storyId);
		firebaseNotificationMapper.deleteFirebaseNotificationRegardingStoryByStoryId(storyId);

		for (File file : storyFiles) {
			int type = file.getType();
			String fileName = file.getName();
			String ext = file.getExt();

			if (type == FileFlag.PHOTO_TYPE) {
				awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_IMAGE_URL, fileName, ext);
			} else if (type == FileFlag.VR360_TYPE) {
				awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_VR360_URL, fileName, ext);
			} else {
				awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_VIDEO_URL, fileName, ext);
			}
		}

		for (File file : commentFiles) {
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

	/**
	 * 스토리 수정 함수
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyDto
	 * @param files
	 * 
	 * @return long
	 * @throws Exception
	 * 
	 */
	@Override
	public Story updateStory(StoryDto storyDto, List<MultipartFile> multipartFiles) throws Exception {
		Story story = storyDto.getStory();
		User user = story.getUser();
		List<File> removeFiles = storyDto.getRemoveFiles();
		int removeFileSize = removeFiles.size();

		storyMapper.updateStory(story);
		long storyId = story.getId();

		if (removeFileSize > 0) {
			fileMapper.deleteFiles(removeFiles);

			for (File file : removeFiles) {
				int type = file.getType();
				String fileName = file.getName();
				String ext = file.getExt();

				if (type == FileFlag.PHOTO_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_IMAGE_URL, fileName, ext);
				} else if (type == FileFlag.VR360_TYPE) {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_VR360_URL, fileName, ext);
				} else {
					awsS3Util.deleteFileByLocationAndFileNameAndExt(STORY_VIDEO_URL, fileName, ext);
				}
			}
		}

		int multipartFileSize = multipartFiles.size();

		if (multipartFileSize > 0) {
			List<File> files = new ArrayList<File>();

			for (int i = 0; i < multipartFileSize; i++) {
				File file = new File();
				file.setStory(story);

				User storyUser = story.getUser();
				file.setUser(storyUser);
				file.setPostCategoryId(FileFlag.STORY_POST);

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

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(STORY_IMAGE_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(STORY_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}

				if (fileType.equals(FileFlag.VR360_CONTENT_TYPE)) {
					String ext = orgname.substring(orgname.lastIndexOf(".") + 1, orgname.length());
					file.setExt(ext);
					file.setType(FileFlag.VR360_TYPE);

					fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(STORY_VR360_URL,
							multipartFile.getInputStream(), ext);

				}

				if (fileType.equals(FileFlag.VIDEO_THUMBNAIL_CONTENT_TYPE)) {
					String ext = FileFlag.VIDEO_THUMBNAIL_EXT;
					file.setExt(ext);
					file.setType(FileFlag.VIDEO_THUMBNAIL_TYPE);
					fileName = awsS3Util.selectFileNameByUploadingFileAndFileNameAndExtToTheLocation(STORY_VIDEO_URL,
							multipartFile.getInputStream(), orgname, ext);

				}

				file.setName(fileName);
				files.add(file);
			}

			fileMapper.insertFiles(files);
		}

		List<StoryTag> storyTags = story.getStoryTags();
		int storyTagSize = storyTags.size();

		storyTagMapper.deleteStoryTagByStoryId(storyId);

		if (storyTagSize > 0) {

			for (int i = 0; i < storyTagSize; i++) {
				storyTags.get(i).setStory(story);
			}
			storyTagMapper.insertStoryTags(storyTags);
		}
		
		long userId = user.getId();
		
		return storyMapper.selectStoryByIdAndUserId(storyId, userId);
	}

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
	 */
	@Override
	public Story selectStoryByIdAndUserId(long id, long userId) throws Exception {
		return storyMapper.selectStoryByIdAndUserId(id, userId);
	}

	/**
	 * 월별 좋아요 많고 사진있는 best5 스토리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	@Override
	public List<Story> selectBestStoryListPerMonthLimitFive() throws Exception {
		return storyMapper.selectBestStoryListPerMonthLimitFive();
	}

}
