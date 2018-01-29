package com.djunderworld.stm.user.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.djunderworld.stm.behavior.service.BehaviorService;
import com.djunderworld.stm.common.dao.Behavior;
import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MerchantFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.flag.NotificationFlag;
import com.djunderworld.stm.firebasenotification.service.FirebaseNotificationService;
import com.djunderworld.stm.user.service.UserService;

/**
 * 유저관련 컨트롤러
 * 
 * @author dongjooKim
 */
@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private FirebaseNotificationService firebaseNotificationService;

	@Autowired
	private BehaviorService behaviorService;

	/**
	 * 이메일 중복체크 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param email
	 * @return Boolean
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/emailCheck.json", method = RequestMethod.GET)
	@ResponseBody
	public Boolean selectCheckByEmail(@RequestParam("email") String email) throws Exception {
		return userService.selectCheckByEmail(email);
	}

	/**
	 * 이메일로 유저 정보 검색 API
	 * 
	 * @author dongjooKim
	 *
	 * @param email
	 * 
	 * @return User
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/userByEmail.json", method = RequestMethod.GET)
	@ResponseBody
	public User selectUserByEmail(@RequestParam("email") String email) throws Exception {
		return userService.selectUserByEmail(email);
	}

	/**
	 * 유저 비밀번호 리셋 후 메일로 임시 비밀번호 발송 API
	 * 
	 * @author dongjooKim
	 *
	 * @param User
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public void updateUserByResettingPassword(@RequestBody User user) throws Exception {
		userService.updateUserByResettingPassword(user);
	}

	/**
	 * 회원가입 (일반인, 상인 구분) API
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * @return redirect:login
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String insertUser(@RequestBody User user, RedirectAttributes redirectAttributes) throws Exception {
		userService.insertUser(user);

		String email = user.getEmail();
		String password = user.getPassword();

		redirectAttributes.addAttribute("email", email);
		redirectAttributes.addAttribute("password", password);

		return "redirect:/users/login.json";
	}

	/**
	 * 로그인 API (성공시, 엑세스토큰이 담긴 User 반환, 실패시, null)
	 * 
	 * @author dongjooKim
	 * 
	 * @param email
	 * @param password
	 * @return User
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.json", method = RequestMethod.GET)
	@ResponseBody
	public User selectUserByEmailAndPassword(@RequestParam("email") String email,
			@RequestParam("password") String password) throws Exception {
		return userService.selectUserByEmailAndPassword(email, password);
	}

	/**
	 * 키워드 검색(사람이름, 이메일)으로 유저 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param keyword
	 * @param userId
	 * @param offset
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/search.json", method = RequestMethod.GET)
	@ResponseBody
	public List<User> selectUserListByKeywordAndOffset(@RequestParam("keyword") String keyword,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectUserListByKeywordAndOffset(keyword, userId, offset);
	}

	/**
	 * 유저 정보 수정 API(파일: 아바타, 커버 사진)
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param user
	 * @param file
	 * @return User
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST, RequestMethod.PUT })
	@ResponseBody
	public User updateUserById(@PathVariable long id, @RequestPart User user,
			@RequestPart(required = false) List<MultipartFile> files) throws Exception {
		return userService.updateUserByIdAndMultipartFiles(user, files);
	}

	/**
	 * 유저 정보 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param userId
	 * @return User
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public User selectUserById(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId) throws Exception {
		return userService.selectUserById(id, userId);
	}

	/**
	 * 유저 아이디로 스토리 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param userId
	 * @param offset
	 * @return List<Story>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/stories.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Story> selectStoryListByIdAndOffset(@PathVariable long id,
			@RequestParam(name = "userId", required = false, defaultValue = "0") long userId,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectStoryListByStoryUserIdAndUserIdAndOffset(id, userId, offset);
	}

	/**
	 * 유저 아이디로 스토리 파일 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param type
	 * @param offset
	 * @return List<File>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/files/{type}.json", method = RequestMethod.GET)
	@ResponseBody
	public List<File> selectFileListByIdAndTypeAndOffset(@PathVariable long id, @PathVariable int type,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectFileListByIdAndTypeAndOffset(id, type, offset);
	}

	/**
	 * 상인 아이디로 팔로우 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param offset
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/followers.json", method = RequestMethod.GET)
	@ResponseBody
	public List<User> selectFollowerListByMerchantIdAndOffset(@PathVariable long id,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectFollowerListByMerchantIdAndOffset(id, offset);
	}

	/**
	 * 유저가 팔로우하는 시장 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param offset
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/followingMarkets.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Market> selectFollowingMarketListByIdAndOffset(@PathVariable long id,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectFollowingMarketListByIdAndOffset(id, offset);
	}

	/**
	 * 유저가 팔로우하는 상인 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/followingMerchants.json", method = RequestMethod.GET)
	@ResponseBody
	public List<User> selectFollowingMerchantListByIdAndOffset(@PathVariable long id,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectFollowingMerchantListByIdAndOffset(id, offset);
	}

	/**
	 * 상인 팔로우 생성 API (파이어베이스 알림 기능)
	 * 
	 * @author dongjooKim
	 * 
	 * @param id
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}/follow", method = RequestMethod.POST)
	@ResponseBody
	public void insertMerchantFollower(@PathVariable long id, @RequestBody MerchantFollower merchantFollower)
			throws Exception {
		User storyUser = merchantFollower.getMerchant();
		User user = merchantFollower.getUser();

		long storyUserId = storyUser.getId();
		long userId = user.getId();
		userService.insertMerchantFollower(merchantFollower);

		FirebaseNotification firebaseNotification = new FirebaseNotification();
		firebaseNotification.setUser(user);
		firebaseNotification.setReceiverCategoryId(NotificationFlag.TO_MERCHANT);
		firebaseNotification.setReceiverId(storyUserId);
		firebaseNotification.setContent(NotificationFlag.CONTENT_OBJECT_OF_FOLLOWING_MERCHANT);
		firebaseNotification.setNavigationCategoryId(NotificationFlag.NAVIGATE_TO_THE_PAGE_OF_USER);
		firebaseNotification.setNavigationId(userId);

		Behavior behavior = behaviorService.selectBehaviorById(NotificationFlag.FOLLOWING_THE_MERCHANT);
		firebaseNotification.setBehavior(behavior);

		long firebaseNotificationId = firebaseNotificationService.insertFirebaseNotification(firebaseNotification);
		firebaseNotification.setId(firebaseNotificationId);
		firebaseNotificationService.insertUserNotificationByFirebaseNotification(firebaseNotification);
	}

	/**
	 * 상인 팔로우 삭제 API
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{id}/follow", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteMerchantFollower(@PathVariable long id, @RequestBody MerchantFollower merchantFollower)
			throws Exception {
		userService.deleteMerchantFollower(merchantFollower);
	}

	/**
	 * 월별 스토리 작성 활동이 많은 best 5 유저 검색 API
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/best5.json", method = RequestMethod.GET)
	@ResponseBody
	public List<User> selectBestMerchantListPerMonthLimitFive() throws Exception {
		return userService.selectBestMerchantListPerMonthLimitFive();
	}

	/**
	 * 유저 파이어베이스 알림 리스트 검색 API
	 * 
	 * @author dongjooKim
	 * @param id
	 * @param offset
	 * 
	 * @return List<FirebaseNotification>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/notifications.json", method = RequestMethod.GET)
	@ResponseBody
	public List<FirebaseNotification> selectFirebaseNotificationListById(@PathVariable long id,
			@RequestParam("offset") long offset) throws Exception {
		return userService.selectFirebaseNotificationListByIdAndOffset(id, offset);
	}

}
