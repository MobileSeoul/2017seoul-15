package com.djunderworld.stm.firebasenotification.service.impl;

/**
 * 파이어베이스 알림 관련 서비스 레이어 인터페이스 구현 클래스
 * 
 * @author dongjooKim
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.djunderworld.stm.common.dao.Behavior;
import com.djunderworld.stm.common.dao.FirebaseNotification;
import com.djunderworld.stm.common.dao.User;
import com.djunderworld.stm.common.dto.Data;
import com.djunderworld.stm.common.dto.DataMessage;
import com.djunderworld.stm.common.dto.FirebaseResponse;
import com.djunderworld.stm.common.flag.NotificationFlag;
import com.djunderworld.stm.common.utils.FirebaseCloudMessageUtil;
import com.djunderworld.stm.firebasenotification.service.FirebaseNotificationService;
import com.djunderworld.stm.user.service.impl.UserMapper;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FirebaseNotificationServiceImpl implements FirebaseNotificationService {

	@Autowired
	private FirebaseNotificationMapper firebaseNotificationMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FirebaseCloudMessageUtil firebaseCloudMessageUtil;

	/**
	 * FirebaseNotification DB insert 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @return long
	 * @throws Exception
	 */
	@Override
	public long insertFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception {
		firebaseNotificationMapper.insertFirebaseNotification(firebaseNotification);
		long firebaseNotificationId = firebaseNotification.getId();

		return firebaseNotificationId;
	}

	/**
	 * UserNotification DB insert 및 FirebaseNotification Data Message HttpEntity
	 * 전송 함수
	 * 
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @throws Exception
	 */
	@Override
	public void insertUserNotificationByFirebaseNotification(FirebaseNotification firebaseNotification)
			throws Exception {
		long firebaseNotificationId = firebaseNotification.getId();
		User user = firebaseNotification.getUser();
		long userId = user.getId();
		long receiverCategoryId = firebaseNotification.getReceiverCategoryId();
		long receiverId = firebaseNotification.getReceiverId();
		String userName = user.getName();
		String content = firebaseNotification.getContent();
		Behavior behavior = firebaseNotification.getBehavior();
		String behaviorName = behavior.getName();

		List<User> users = null;

		if (receiverCategoryId == NotificationFlag.TO_ME || receiverCategoryId == NotificationFlag.TO_WRITER
				|| receiverCategoryId == NotificationFlag.TO_MERCHANT) {
			User toUser = userMapper.selectUserById(receiverId);
			users = new ArrayList<User>();
			users.add(toUser);
		}

		if (receiverCategoryId == NotificationFlag.TO_FOLLOWERS) {
			users = userMapper.selectFollowerFcmTonkenListByMerchantId(receiverId);
		}

		int userSize = users.size();
		if (userSize > 0) {
			firebaseNotificationMapper.insertUserNotificationByfirebaseNotificationIdAndUsers(firebaseNotificationId,
					users);

			DataMessage dataMessage = new DataMessage();
			String dataMessageBody = userName + "님이 " + content + behaviorName;
			Data data = new Data(dataMessageBody);

			dataMessage.setPriority(NotificationFlag.HIGH_PRIORITY);
			dataMessage.setRegistration_ids(dataMessage.convertToRegistrationIdArrayFromUserList(users));
			dataMessage.setData(data);

			HttpHeaders httpHeaders = firebaseCloudMessageUtil.getHttpHeaders();
			HttpEntity<DataMessage> httpEntity = new HttpEntity<DataMessage>(dataMessage, httpHeaders);

			CompletableFuture<FirebaseResponse> completableFuture = firebaseCloudMessageUtil
					.postDataMessage(httpEntity);
		}

	}

	/**
	 * FirebaseNotification 수정 함수
	 * 
	 * @author dongjooKim
	 * @param firebaseNotification
	 * @throws Exception
	 */
	@Override
	public void updateFirebaseNotification(FirebaseNotification firebaseNotification) throws Exception {
		firebaseNotificationMapper.updateFirebaseNotification(firebaseNotification);
	}

}
