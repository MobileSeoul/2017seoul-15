package com.djunderworld.stm.common.dao;

/**
 * 
 * 유저관련 파이어 베이스 알림 정보 엔티티 클래스
 * 
 * 수신자 정보, 파이어베이스 알림 정보
 * 
 * @author dongjooKim
 */
public class UserNotification extends Base {
	private User user;
	private FirebaseNotification firebaseNotification;

	public UserNotification() {
		super();
	}

	public UserNotification(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	@Override
	public long getId() {
		return super.getId();
	}

	@Override
	public void setId(long id) {
		super.setId(id);
	}

	@Override
	public String getCreatedAt() {
		return super.getCreatedAt();
	}

	@Override
	public void setCreatedAt(String createdAt) {
		super.setCreatedAt(createdAt);
	}

	@Override
	public String getUpdatedAt() {
		return super.getUpdatedAt();
	}

	@Override
	public void setUpdatedAt(String updatedAt) {
		super.setUpdatedAt(updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FirebaseNotification getFirebaseNotification() {
		return firebaseNotification;
	}

	public void setFirebaseNotification(FirebaseNotification firebaseNotification) {
		this.firebaseNotification = firebaseNotification;
	}

}
