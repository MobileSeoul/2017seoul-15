package com.djunderworld.stm.firebasenotification.service;

import com.djunderworld.stm.common.dao.FirebaseNotification;

/**
 * 파이어베이스 알림 관련 서비스 레이어 인터페이스
 * 
 * @author dongjooKim
 * 
 */
public interface FirebaseNotificationService {

	/**
	 * FirebaseNotification DB insert 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @return long
	 * @throws Exception
	 */
	long insertFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception;

	/**
	 * UserNotification DB insert 및 FirebaseNotification Data Message HttpEntity
	 * 전송 함수
	 * 
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @throws Exception
	 */
	void insertUserNotificationByFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception;

	/**
	 * FirebaseNotification 수정 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @throws Exception
	 */
	void updateFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception;

}
