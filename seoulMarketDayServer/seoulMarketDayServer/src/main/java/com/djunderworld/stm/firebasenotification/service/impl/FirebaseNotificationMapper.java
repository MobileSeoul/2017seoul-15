package com.djunderworld.stm.firebasenotification.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.MerchantFollower;
import com.djunderworld.stm.common.dao.User;

/**
 * 파이어베이스 알림 관련 레파지토리 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
@Repository
public interface FirebaseNotificationMapper {

	/**
	 * 파이어베이스 알림 생성 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @return long
	 * @throws Exception
	 */
	long insertFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception;

	/**
	 * 유저리스트 별 유저관련 파이어 베이스 알림 생성 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotificationId
	 * @param users
	 * 
	 * @throws Exception
	 */
	void insertUserNotificationByfirebaseNotificationIdAndUsers(
			@Param("firebaseNotificationId") long firebaseNotificationId, @Param("users") List<User> users)
			throws Exception;

	/**
	 * 스토리 아이디 별 스토리관련 파이어베이스 알림전체 삭제 함수
	 * 
	 * @author dongjooKim
	 * @param storyId
	 * 
	 * @throws Exception
	 */
	void deleteFirebaseNotificationRegardingStoryByStoryId(@Param("storyId") long storyId) throws Exception;

	/**
	 * 유저 아이디 및 오프셋 별로 파이어베이스 알림 리스트 검색 매핑함수
	 * 
	 * @author dongjooKim
	 * @param userId
	 * @param offset
	 * 
	 * @throws Exception
	 * @return List<FirebaseNotification>
	 */
	List<FirebaseNotification> selectFirebaseNotificationListByUserIdAndOffset(@Param("userId") long userId,
			@Param("offset") long offset) throws Exception;

	void updateFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception;

	/**
	 * 상인팔로우 관련 파이어베이스 알림전체 삭제 함수
	 * 
	 * @author dongjooKim
	 * @param merchantFollower
	 * 
	 * @throws Exception
	 */
	void deleteFirebaseNotificationRegardingMerchantFollower(MerchantFollower merchantFollower) throws Exception;

	/**
	 * 스토리 좋아요관련 파이어베이스 알림전체 삭제 함수
	 * 
	 * @author dongjooKim
	 * @param storyId
	 * @param userId
	 * 
	 * @throws Exception
	 */
	void deleteFirebaseNotificationRegardingStoryLikeByStoryIdAndUserId(@Param("storyId") long storyId,
			@Param("userId") long userId) throws Exception;

}
