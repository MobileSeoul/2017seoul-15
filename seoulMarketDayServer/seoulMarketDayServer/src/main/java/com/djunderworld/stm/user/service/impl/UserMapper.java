package com.djunderworld.stm.user.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.User;

/**
 * 유저 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 */
@Repository
public interface UserMapper {

	/**
	 * 이메일 중복체크 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param email
	 * @return Boolean
	 * 
	 * @throws Exception
	 */
	Boolean selectCheckByEmail(@Param("email") String email) throws Exception;

	/**
	 * 유저 생성 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * 
	 * @throws Exception
	 */
	void insertUser(User user) throws Exception;

	/**
	 * 로그인 기능, 이메일과 비밀번호로 유저 검색 매핑 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param email
	 * @param password
	 * 
	 * @return User
	 * 
	 * @throws Exception
	 */
	User selectUserByEmailAndPassword(@Param("email") String email, @Param("password") String password)
			throws Exception;

	/**
	 * 유저 수정 함수
	 * 
	 * @author dongjooKim
	 * 
	 * @param user
	 * 
	 * @throws Exception
	 */
	void updateUserById(User user) throws Exception;

	/**
	 * 유저 아이디로 유저 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * 
	 * @return User
	 * 
	 * @throws Exception
	 */
	User selectUserById(@Param("id") long id) throws Exception;

	/**
	 * 상인아이디로 팔로우하는 유저들 파이어베이스 토큰 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param merchantId
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectFollowerFcmTonkenListByMerchantId(@Param("merchantId") long merchantId) throws Exception;

	/**
	 * 시장아이디로 소속 상인 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param marketId
	 * @param userId
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectMerchantListByMarketIdAndOffset(@Param("marketId") long marketId, @Param("offset") long offset,
			@Param("userId") long userId) throws Exception;

	/**
	 * 상인아이디로 해당 상인 팔로우 하는 유저 리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param merchantId
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectFollowerListByMerchantIdAndOffset(@Param("merchantId") long merchantId,
			@Param("offset") long offset) throws Exception;

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
	 * 유저아이디로 해당 유저가 팔로우하는 상인리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param id
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectFollowingMerchantListByIdAndOffset(@Param("id") long id, @Param("offset") long offset)
			throws Exception;

	/**
	 * 키워드로 유저리스트 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param keyword
	 * @param userId
	 * @param offset
	 * 
	 * @return List<User>
	 * 
	 * @throws Exception
	 */
	List<User> selectUserListByKeywordAndOffset(@Param("keyword") String keyword, @Param("userId") long userId,
			@Param("offset") long offset) throws Exception;

	/**
	 * 이메일로 해당 유저 검색 함수
	 * 
	 * @author dongjooKim
	 * 
	 * 
	 * @param email
	 * 
	 * @return User
	 * 
	 * @throws Exception
	 */
	User selectUserByEmail(@Param("email") String email) throws Exception;

}
