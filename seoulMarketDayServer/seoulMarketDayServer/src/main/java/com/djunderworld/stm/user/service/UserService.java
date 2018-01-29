package com.djunderworld.stm.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.Market;
import com.djunderworld.stm.common.dao.MerchantFollower;
import com.djunderworld.stm.common.dao.Story;
import com.djunderworld.stm.common.dao.User;

/**
 * 유저 관련 서비스레이어 인터페이스
 * 
 * @author dongjooKim
 */
public interface UserService {

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
	Boolean selectCheckByEmail(String email) throws Exception;

	/**
	 * 회원가입 함수 (일반인, 상인 구분)
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * 
	 * @throws Exception
	 */
	void insertUser(User user) throws Exception;

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
	User selectUserByEmailAndPassword(String email, String password) throws Exception;

	/**
	 * 유저 정보 수정 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * @param file
	 * @return User
	 * 
	 * @throws Exception
	 */
	User updateUserByIdAndMultipartFiles(User user, List<MultipartFile> files) throws Exception;

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
	User selectUserById(long id, long userId) throws Exception;

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
	List<Story> selectStoryListByStoryUserIdAndUserIdAndOffset(long id, long userId, long offset) throws Exception;

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
	List<File> selectFileListByIdAndTypeAndOffset(long id, int type, long offset) throws Exception;

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
	List<User> selectFollowerListByMerchantIdAndOffset(long id, long offset) throws Exception;

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
	void insertMerchantFollower(MerchantFollower merchantFollower) throws Exception;

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
	void deleteMerchantFollower(MerchantFollower merchantFollower) throws Exception;

	/**
	 * 월별 스토리 작성 활동이 많은 best 5 유저 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectBestMerchantListPerMonthLimitFive() throws Exception;

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
	List<FirebaseNotification> selectFirebaseNotificationListByIdAndOffset(long id, long offset) throws Exception;

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
	List<Market> selectFollowingMarketListByIdAndOffset(long id, long offset) throws Exception;

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
	List<User> selectFollowingMerchantListByIdAndOffset(long id, long offset) throws Exception;

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
	List<User> selectUserListByKeywordAndOffset(String keyword, long userId, long offset) throws Exception;

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
	User selectUserByEmail(String email) throws Exception;

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
	void updateUserByResettingPassword(User user) throws Exception;

}
