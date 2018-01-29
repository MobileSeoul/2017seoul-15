package com.djunderworld.stm.user.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MerchantFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.FirebaseResponse;
import com.djunderworld.stm.common.dto.Notification;
import com.djunderworld.stm.common.dto.NotificationMessage;
import com.djunderworld.stm.common.flag.FileFlag;
import com.djunderworld.stm.common.flag.NotificationFlag;
import com.djunderworld.stm.common.flag.UserFlag;
import com.djunderworld.stm.common.utils.AwsS3Util;
import com.djunderworld.stm.common.utils.FirebaseCloudMessageUtil;
import com.djunderworld.stm.common.utils.JwtUtil;
import com.djunderworld.stm.file.service.impl.FileMapper;
import com.djunderworld.stm.firebasenotification.service.impl.FirebaseNotificationMapper;
import com.djunderworld.stm.market.service.impl.MarketMapper;
import com.djunderworld.stm.marketuser.service.impl.MarketUserMapper;
import com.djunderworld.stm.merchantfollower.service.impl.MerchantFollowerMapper;
import com.djunderworld.stm.story.service.impl.StoryMapper;
import com.djunderworld.stm.user.service.UserService;

/**
 * 유저 관련 서비스레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MarketUserMapper marketUserMapper;

	@Autowired
	private StoryMapper storyMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private MerchantFollowerMapper merchantFollowerMapper;

	@Autowired
	private MarketMapper marketMapper;

	@Autowired
	private FirebaseNotificationMapper firebaseNotificationMapper;

	@Autowired
	private AwsS3Util awsS3Util;

	@Autowired
	private FirebaseCloudMessageUtil firebaseCloudMessageUtil;

	@Value("#{aws['aws.s3.avatar.image.url']}")
	private String USER_AVATAR_URL;

	@Value("#{aws['aws.s3.cover.image.url']}")
	private String USER_COVER_URL;

	@Value("#{mail['mail.address']}")
	private String ADMIN_MAIL_ADDRESS;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 이메일 중복체크 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param email
	 * @return Boolean
	 * 
	 * @throws Exception
	 */
	@Override
	public Boolean selectCheckByEmail(String email) throws Exception {
		return userMapper.selectCheckByEmail(email);
	}

	/**
	 * 회원가입 함수 (일반인, 상인 구분)
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * 
	 * @throws Exception
	 */
	@Override
	public void insertUser(User user) throws Exception {
		Market market = user.getMarket();

		userMapper.insertUser(user);

		if (market != null) {
			long userId = user.getId();
			long marketId = market.getId();
			marketUserMapper.insertMarketUser(marketId, userId);
		}
	}

	/**
	 * 로그인 함수 (성공시, 엑세스토큰이 담긴 User 반환, 실패시, null)
	 * 
	 * @author dongjooKim
	 * 
	 * @param email
	 * @param password
	 * @return User
	 * 
	 * @throws Exception
	 */
	@Override
	public User selectUserByEmailAndPassword(String email, String password) throws Exception {

		User user = userMapper.selectUserByEmailAndPassword(email, password);

		if (user != null) {
			int level = user.getLevel();
			String accessToken = jwtUtil.generateToken(level);
			user.setAccessToken(accessToken);
		}

		return user;
	}

	/**
	 * 유저 정보 수정 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * @param file
	 * 
	 * @throws Exception
	 */
	@Override
	public User updateUserByIdAndMultipartFiles(User user, List<MultipartFile> multipartFiles) throws Exception {

		int multipartFileSize = multipartFiles.size();

		if (multipartFileSize > 0) {
			for (int i = 0; i < multipartFileSize; i++) {
				MultipartFile multipartFile = multipartFiles.get(i);

				String fileType = multipartFile.getContentType().trim();
				String orgname = multipartFile.getOriginalFilename();
				String ext = orgname.substring(orgname.lastIndexOf(".") + 1, orgname.length());

				if (fileType.equals(FileFlag.USER_AVATAR_CONTENT_TYPE)) {
					String prevAvatar = user.getAvatar();
					if (!prevAvatar.equals(FileFlag.USER_AVATAR_DEFAULT)) {
						awsS3Util.deleteFileByLocationAndFileName(USER_AVATAR_URL, prevAvatar);
					}

					String fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(USER_AVATAR_URL,
							multipartFile.getInputStream(), ext);
					String avatar = fileName + "." + ext;
					user.setAvatar(avatar);
				}

				if (fileType.equals(FileFlag.USER_COVER_CONTENT_TYPE)) {
					String prevCover = user.getCover();
					if (!prevCover.equals(FileFlag.USER_COVER_DEFAULT)) {
						awsS3Util.deleteFileByLocationAndFileName(USER_COVER_URL, prevCover);
					}

					String fileName = awsS3Util.selectFileNameByUploadingFileAndExtToTheLocation(USER_COVER_URL,
							multipartFile.getInputStream(), ext);

					String cover = fileName + "." + ext;
					user.setCover(cover);
				}

			}
		}
		long userId = user.getId();
		String deviceId = user.getDeviceId();

		if (deviceId != null && deviceId.length() > 0) {
			User prevUser = userMapper.selectUserById(userId);
			String prevDeviceId = prevUser.getDeviceId();

			if (prevDeviceId != null && prevDeviceId.length() > 0 && !deviceId.equals(prevDeviceId)) {
				String fcmToken = prevUser.getFcmToken();

				NotificationMessage notificationMessage = new NotificationMessage();
				String content = NotificationFlag.CONTENT_OF_FORCED_CLOSE_FOR_DUPLICATIVE_LOGIN;
				Notification notification = new Notification(content);

				notificationMessage.setPriority(NotificationFlag.HIGH_PRIORITY);
				notificationMessage.setTo(fcmToken);
				notificationMessage.setNotification(notification);

				HttpHeaders httpHeaders = firebaseCloudMessageUtil.getHttpHeaders();
				HttpEntity<NotificationMessage> httpEntity = new HttpEntity<NotificationMessage>(notificationMessage,
						httpHeaders);

				CompletableFuture<FirebaseResponse> completableFuture = firebaseCloudMessageUtil
						.postNotificationMessage(httpEntity);
			}
		}

		userMapper.updateUserById(user);

		return userMapper.selectUserById(userId);
	}

	/**
	 * 유저 정보 검색 함수
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
	@Override
	public User selectUserById(long id, long userId) throws Exception {
		User user = userMapper.selectUserById(id);
		int userLevel = user.getLevel();

		if (userLevel == UserFlag.ROLE_MERCHANT) {
			Market market = marketMapper.selectMarketByUserId(id);
			user.setMarket(market);

			int followerCount = merchantFollowerMapper.selectMerchantFollowerCountByMerchantId(id);
			user.setFollowerCount(followerCount);

			if (userId > 0 && id != userId) {
				Boolean isFollowed = merchantFollowerMapper.selectFollowCheckByMerchantIdAndUserId(id, userId);
				user.setIsFollowed(isFollowed);
			} else {
				user.setIsFollowed(false);
			}
		}

		return user;
	}

	/**
	 * 유저 아이디로 스토리 리스트 검색 함수
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
	@Override
	public List<Story> selectStoryListByStoryUserIdAndUserIdAndOffset(long id, long userId, long offset)
			throws Exception {
		return storyMapper.selectStoryListByStoryUserIdAndUserIdAndOffset(id, userId, offset);
	}

	/**
	 * 유저 아이디로 스토리 파일 리스트 검색 함수
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
	@Override
	public List<File> selectFileListByIdAndTypeAndOffset(long id, int type, long offset) throws Exception {
		return fileMapper.selectFileListByUserIdAndTypeAndOffset(id, type, offset);
	}

	/**
	 * 상인 아이디로 팔로우 리스트 검색 함수
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
	@Override
	public List<User> selectFollowerListByMerchantIdAndOffset(long id, long offset) throws Exception {
		return userMapper.selectFollowerListByMerchantIdAndOffset(id, offset);
	}

	/**
	 * 상인 팔로우 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	@Override
	public void insertMerchantFollower(MerchantFollower merchantFollower) throws Exception {
		merchantFollowerMapper.insertMerchantFollower(merchantFollower);
	}

	/**
	 * 상인 팔로우 삭제 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	@Override
	public void deleteMerchantFollower(MerchantFollower merchantFollower) throws Exception {
		merchantFollowerMapper.deleteMerchantFollower(merchantFollower);
		firebaseNotificationMapper.deleteFirebaseNotificationRegardingMerchantFollower(merchantFollower);
	}

	/**
	 * 월별 스토리 작성 활동이 많은 best 5 유저 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<User> selectBestMerchantListPerMonthLimitFive() throws Exception {
		return userMapper.selectBestMerchantListPerMonthLimitFive();
	}

	/**
	 * 유저 알림 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * @param id
	 * @param offset
	 * 
	 * @return List<FirebaseNotification>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<FirebaseNotification> selectFirebaseNotificationListByIdAndOffset(long id, long offset)
			throws Exception {
		return firebaseNotificationMapper.selectFirebaseNotificationListByUserIdAndOffset(id, offset);
	}

	/**
	 * 유저가 팔로우하는 시장 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * @param id
	 * @param offset
	 * 
	 * @return List<Market>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Market> selectFollowingMarketListByIdAndOffset(long id, long offset) throws Exception {
		return marketMapper.selectFollowingMarketListByUserIdAndOffset(id, offset);
	}

	/**
	 * 유저가 팔로우하는 상인 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * @param id
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	@Override
	public List<User> selectFollowingMerchantListByIdAndOffset(long id, long offset) throws Exception {
		return userMapper.selectFollowingMerchantListByIdAndOffset(id, offset);
	}

	/**
	 * 키워드 검색(사람이름, 이메일)으로 유저 리스트 검색 함수
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
	@Override
	public List<User> selectUserListByKeywordAndOffset(String keyword, long userId, long offset) throws Exception {
		return userMapper.selectUserListByKeywordAndOffset(keyword, userId, offset);
	}

	/**
	 * 이메일로 유저 정보 검색 함수
	 * 
	 * @author dongjooKim
	 *
	 * @param email
	 * @return User
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public User selectUserByEmail(String email) throws Exception {
		return userMapper.selectUserByEmail(email);
	}

	/**
	 * 유저 비밀번호 리셋 후 메일로 임시 비밀번호 발송 함수
	 * 
	 * @author dongjooKim
	 *
	 * @param User
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public void updateUserByResettingPassword(User user) throws Exception {
		long userId = user.getId();
		String userEmail = user.getEmail();
		String userName = user.getName();

		String ramdomPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
		String encryptedPassword = encryptPasswordWithSHA1(ramdomPassword);

		User newUser = new User();
		newUser.setId(userId);
		newUser.setPassword(encryptedPassword);

		userMapper.updateUserById(newUser);

		MimeMessage message = mailSender.createMimeMessage();
		Address email = new InternetAddress(userEmail);

		message.addRecipient(Message.RecipientType.TO, email);
		message.setFrom(ADMIN_MAIL_ADDRESS);
		message.setSubject("서울장날, 임시 비밀번호가 발급되었습니다.");

		String content = "<div style=\"max-width: 595px; margin: 0 auto\">"
				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 595px; width: 100%; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; background-color: #fff; -webkit-text-size-adjust: 100%; text-align: left"
				+ "align=\"center\"><tbody><tr><td height=\"30\"></td></tr><tr><td style=\"padding-right: 27px; padding-left: 15px\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
				+ "<tbody><tr><td style=\" width=\"61\"\"><img  src=\"http://d3fmxlpcykzndk.cloudfront.net/stm/images/logos/logo.png\" alt=\"Logo\" width=\"180\"></td></tr></tbody></table></td></tr>"
				+ "<tr><td height=\"13\"></td></tr>"
				+ "<tr><td style=\"padding-right: 27px; padding-left: 18px; line-height: 34px; font-size: 29px; color: #424240; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
				+ "회원님의 <br><span style=\"color: #542920\">임시 비밀번호</span>가 발급되었습니다. </td></tr>"
				+ "<tr><td height=\"22\"></td></tr><tr>   <td height=\"1\" style=\"background-color: #e5e5e5;\"></td></tr>"

				+ "<tr><td style=\"padding-top: 24px; padding-right: 27px; padding-bottom: 32px; padding-left: 20px\">"

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" width=\"100%\"> <tbody> "
				+ "<tr><td height=\"24\"></td></tr>"
				+ "<tr><td style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"

				+ "<table cellpadding=\"0\" cellspacing=\"0\"    style=\"width: 100%; margin: 0; padding: 0\"> <tbody>"
				+ "<tr><td height=\"23\"    style=\"font-weight: bold; color: #000; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">발급 정보</td></tr>"

				+ "<tr><td height=\"2\" style=\"background: #424240\"></td> </tr> <tr> <td height=\"20\"></td></tr>"
				+ "<tr><td>"
				+ "<table cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; margin: 0; padding: 0\"> <tbody> <tr> <td width=\"110\" style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
				+ "이메일</td><td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
				+ email + "</span>" + "</td></tr>"

				+ "<tr><td width=\"110\"style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">임시 비밀번호</td>"
				+ "<td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
				+ ramdomPassword + "</span></td>" + "</tr><tr>"

				+ "<td width=\110\" style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">이름</td>"
				+ "<td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
				+ userName + "</span></td></tr>" + "</tbody></table>" + "</td></tr>"
				+ "<tr><td height=\"20\"></td></tr>" + "<tr><td height=\"1\" style=\"background: #d5d5d5\"></td></tr>"
				+ "</tbody></table>"

				+ "</td></tr>" + "<tr><td height=\"24\"></td></tr>"
				+ "<tr><td   style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
				+ "   <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" width=\"100%\"><tbody>"
				+ "<tr><td style=\"width: 2px; padding-top: 2px; padding-right: 5px; vertical-align: top;\">-</td>"
				+ "<td style=\"text-align: left; vertical-align: middle\">로그인   후 새로운 비밀번호로 변경하여 이용하세요.</td></tr>   <tr><td    style=\"width: 2px; padding-top: 2px; padding-right: 5px; vertical-align: top;\">-</td>   <td style=\"text-align: left; vertical-align: middle\">발급된 비밀번호는 '서울장날' 관리자도 알 수 없도록 암호화 되어 있습니다.</td>"
				+ "</tr></tbody></table></td></tr>" + "<tr><td height=\"24\"></td>" + "</tr>"
				+ "<tr><td   style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
				+ "'서울장날'을 이용해 주셔서 감사합니다.<br> 더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.</td></tr>" + "</tbody></table>"
				+ "</td></tr>"
				+ "<tr><td style=\"padding-top: 26px; padding-left: 21px; padding-right: 21px; padding-bottom: 13px; background: #f9f9f9; font-size: 12px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; color: #696969; line-height: 17px\">"
				+ "본 메일은 발신전용 입니다</td></tr>"
				+ "<tr><td    style=\"padding-left: 21px; padding-right: 21px; padding-bottom: 57px; background: #f9f9f9; font-size: 12px; font-family: Helvetica; color: #696969; line-height: 17px\"> Copyright ⓒ <strong><span style=\"color: #542920; text-decoration: none;\">DJ_underworld</span></strong> Corp.All Rights Reserved.</td></tr>"
				+ "</tbody></table></div>";

		message.setText(content, "utf-8", "html");
		mailSender.send(message);
	}

	/**
	 * SHA-256으로 패스워드 암호화 함수
	 * 
	 * @author dongjooKim
	 *
	 * @param ramdomPassword
	 * @return String
	 * 
	 * @throws Exception
	 * 
	 */
	public String encryptPasswordWithSHA1(String ramdomPassword) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(ramdomPassword.getBytes(), 0, ramdomPassword.length());

		return String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
	}
}
