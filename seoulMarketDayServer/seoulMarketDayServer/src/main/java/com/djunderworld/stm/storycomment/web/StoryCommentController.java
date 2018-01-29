package com.djunderworld.stm.storycomment.web;

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
import com.djunderworld.stm.common.flag.NotificationFlag;
import com.djunderworld.stm.firebasenotification.service.FirebaseNotificationService;
import com.djunderworld.stm.storycomment.service.StoryCommentService;

/**
 * 스토리댓글 관련 컨트롤러
 * 
 * @author dongjooKim
 */
@Controller
@RequestMapping("/storycomments")
public class StoryCommentController {

	@Autowired
	private StoryCommentService storyCommentService;

	@Autowired
	private BehaviorService behaviorService;

	@Autowired
	private FirebaseNotificationService firebaseNotificationService;

	/**
	 * 스토리 댓글 및 파일 생성(데이터베이스 & S3) API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param storyComment
	 * @param files
	 * 
	 * @return StoryComment
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public StoryComment insertStoryCommentComment(@RequestPart StoryComment storyComment,
			@RequestPart(required = false) List<MultipartFile> files, @RequestPart(required = false) User commentUser)
			throws Exception {
		StoryComment newStoryComment = storyCommentService.insertStoryComment(storyComment, files);
		User user = storyComment.getUser();
		long userId = user.getId();

		Story story = storyComment.getStory();
		long storyId = story.getId();
		User storyUser = story.getUser();
		long storyUserId = storyUser.getId();

		if (userId != storyUserId) {
			FirebaseNotification commentFirebaseNotification = new FirebaseNotification();
			commentFirebaseNotification.setUser(user);
			commentFirebaseNotification.setReceiverCategoryId(NotificationFlag.TO_WRITER);
			commentFirebaseNotification.setReceiverId(storyUserId);
			commentFirebaseNotification.setContent(NotificationFlag.CONTENT_OBJECT_OF_WRITING_COMMENT_OR_LIKE);
			commentFirebaseNotification.setNavigationCategoryId(NotificationFlag.NAVIGATE_TO_THE_PAGE_OF_STORY);
			commentFirebaseNotification.setNavigationId(storyId);
			Behavior commentBehavior = behaviorService.selectBehaviorById(NotificationFlag.WRITING_THE_COMMENT);
			commentFirebaseNotification.setBehavior(commentBehavior);

			long commentFirebaseNotificationId = firebaseNotificationService
					.insertFirebaseNotification(commentFirebaseNotification);
			commentFirebaseNotification.setId(commentFirebaseNotificationId);
			firebaseNotificationService.insertUserNotificationByFirebaseNotification(commentFirebaseNotification);

		}

		long depth = storyComment.getDepth();

		if (depth == 1) {
			long commentUserId = commentUser.getId();
			if (userId != commentUserId) {

				FirebaseNotification replyFirebaseNotification = new FirebaseNotification();
				replyFirebaseNotification.setUser(user);
				replyFirebaseNotification.setReceiverCategoryId(NotificationFlag.TO_WRITER);
				replyFirebaseNotification.setReceiverId(commentUserId);
				replyFirebaseNotification.setContent(NotificationFlag.CONTENT_OBJECT_OF_WRITING_REPLY_COMMENT);
				replyFirebaseNotification.setNavigationCategoryId(NotificationFlag.NAVIGATE_TO_THE_PAGE_OF_STORY);
				replyFirebaseNotification.setNavigationId(storyId);
				Behavior replyBehavior = behaviorService.selectBehaviorById(NotificationFlag.WRITING_THE_COMMENT);
				replyFirebaseNotification.setBehavior(replyBehavior);

				long replyFirebaseNotificationId = firebaseNotificationService
						.insertFirebaseNotification(replyFirebaseNotification);
				replyFirebaseNotification.setId(replyFirebaseNotificationId);
				firebaseNotificationService.insertUserNotificationByFirebaseNotification(replyFirebaseNotification);
			}
		}
		return newStoryComment;
	}

	/**
	 * 스토리 댓글 답변 리스트 검색 API
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
	@RequestMapping(value = "/{groupId}/replies.json", method = RequestMethod.GET)
	@ResponseBody
	public List<StoryComment> selectStoryCommentReplyListByGroupId(@PathVariable long groupId,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return storyCommentService.selectStoryCommentReplyListByGroupIdAndUserIdAndOffset(groupId, userId, offset);
	}

	/**
	 * 스토리 댓글 삭제 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param storyComment
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStoryComment(@PathVariable long id, @RequestBody StoryComment storyComment) throws Exception {
		storyCommentService.deleteStoryComment(storyComment);
	}

	/**
	 * 스토리 댓글 수정 API
	 * 
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param storyComment
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateStoryComment(@PathVariable long id, @RequestBody StoryComment storyComment) throws Exception {
		storyCommentService.updateStoryComment(storyComment);
	}

}
