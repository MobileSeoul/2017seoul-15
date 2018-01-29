package com.djunderworld.stm.story.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.behavior.service.BehaviorService;
import com.djunderworld.stm.common.dao.Behavior;
import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.StoryComment;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.StoryDto;
import com.djunderworld.stm.common.flag.NotificationFlag;
import com.djunderworld.stm.firebasenotification.service.FirebaseNotificationService;
import com.djunderworld.stm.story.service.StoryService;

/**
 * 스토리관련 컨트롤러
 * 
 * @author dongjooKim
 */
@Controller
@RequestMapping("/stories")
public class StoryController {
	@Autowired
	private StoryService storyService;

	@Autowired
	private FirebaseNotificationService firebaseNotificationService;

	@Autowired
	private BehaviorService behaviorService;

	/**
	 * 스토리 아이디별 스토리 검색 API
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
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public Story selectStoryById(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId) throws Exception {
		return storyService.selectStoryByIdAndUserId(id, userId);
	}

	/**
	 * 스토리 생성 API
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
	@Secured("ROLE_USER")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public long insertStory(@RequestPart Story story, @RequestPart(required = false) List<MultipartFile> files)
			throws Exception {
		long storyId = storyService.insertStory(story, files);

		User user = story.getUser();
		long userId = user.getId();

		FirebaseNotification firebaseNotification = new FirebaseNotification();
		firebaseNotification.setUser(user);
		firebaseNotification.setReceiverCategoryId(NotificationFlag.TO_FOLLOWERS);
		firebaseNotification.setReceiverId(userId);
		firebaseNotification.setContent(NotificationFlag.CONTENT_OBJECT_OF_WRITING_STORY);
		firebaseNotification.setNavigationCategoryId(NotificationFlag.NAVIGATE_TO_THE_PAGE_OF_STORY);
		firebaseNotification.setNavigationId(storyId);

		Behavior behavior = behaviorService.selectBehaviorById(NotificationFlag.WRITING_THE_STORY);
		firebaseNotification.setBehavior(behavior);

		long firebaseNotificationId = firebaseNotificationService.insertFirebaseNotification(firebaseNotification);
		firebaseNotification.setId(firebaseNotificationId);
		firebaseNotificationService.insertUserNotificationByFirebaseNotification(firebaseNotification);

		return storyId;
	}

	/**
	 * 스토리 수정 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param storyDto
	 * @param files
	 * 
	 * @return Story
	 * @throws Exception
	 * 
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Story updateStory(@PathVariable long id, @RequestPart StoryDto storyDto,
			@RequestPart(required = false) List<MultipartFile> files) throws Exception {
		return storyService.updateStory(storyDto, files);
	}

	/**
	 * 스토리 삭제 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param story
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStory(@PathVariable long id, @RequestBody Story story) throws Exception {
		storyService.deleteStory(story);
	}

	/**
	 * 스토리 좋아요 생성 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @param storyUserId
	 * @param userName
	 * 
	 * @throws Exception
	 * 
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}/like", method = RequestMethod.POST)
	@ResponseBody
	public void insertStoryLikeByIdAndUserId(@PathVariable long id, @RequestParam("storyUserId") long storyUserId,
			@RequestParam("userId") long userId, @RequestParam("userName") String userName) throws Exception {
		storyService.insertStoryLikeByIdAndUserId(id, userId);

		if (storyUserId != userId) {
			User user = new User();
			user.setId(userId);
			user.setName(userName);

			FirebaseNotification firebaseNotification = new FirebaseNotification();
			firebaseNotification.setUser(user);
			firebaseNotification.setReceiverCategoryId(NotificationFlag.TO_WRITER);
			firebaseNotification.setReceiverId(storyUserId);
			firebaseNotification.setContent(NotificationFlag.CONTENT_OBJECT_OF_WRITING_COMMENT_OR_LIKE);
			firebaseNotification.setNavigationCategoryId(NotificationFlag.NAVIGATE_TO_THE_PAGE_OF_STORY);
			firebaseNotification.setNavigationId(id);

			Behavior behavior = behaviorService.selectBehaviorById(NotificationFlag.LIKE_THE_STORY);
			firebaseNotification.setBehavior(behavior);

			long firebaseNotificationId = firebaseNotificationService.insertFirebaseNotification(firebaseNotification);
			firebaseNotification.setId(firebaseNotificationId);
			firebaseNotificationService.insertUserNotificationByFirebaseNotification(firebaseNotification);
		}
	}

	/**
	 * 스토리 좋아요 삭제 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}/like", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStoryLikeByIdAndUserId(@PathVariable long id, @RequestParam("userId") long userId)
			throws Exception {
		storyService.deleteStoryLikeByIdAndUserId(id, userId);
	}

	/**
	 * 스토리 댓글 리스트 검색 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * 
	 * @return List<StoryComment>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/comments.json", method = RequestMethod.GET)
	@ResponseBody
	public List<StoryComment> selectStoryCommentListById(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return storyService.selectStoryCommentListByIdAndUserIdAndOffset(id, userId, offset);
	}

	/**
	 * 월별 좋아요 많고 사진있는 best5 스토리 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<Story>
	 * @throws Exception
	 */
	@RequestMapping(value = "/best5.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Story> selectBestStoryListPerMonthLimitFive() throws Exception {
		return storyService.selectBestStoryListPerMonthLimitFive();
	}

}
