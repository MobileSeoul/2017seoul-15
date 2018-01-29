package com.djunderworld.stm.common.dao;

/**
 * 
 * 파이어 베이스 알림 정보 엔티티 클래스
 * 
 * 행위자 정보, 수신자 아이디, 수신자 유형(1.행위자(나), 2.작성자(상인), 3.팔로우된 상인, 4.팔로워 전체), 알림 주 내용,
 * 페이지 이동 아이디정보(1.스토리, 2.유저), 페이지 이동 유형(1.스토리, 2.유저), 유저 행위 정보, 수신확인 체크 여부
 * 
 * @author dongjooKim
 */
public class FirebaseNotification extends Base {
	private User user;
	private long receiverId;
	private long receiverCategoryId;
	private String content;
	private long navigationId;
	private long navigationCategoryId;
	private Behavior behavior;
	private int checked;

	public FirebaseNotification() {
		super();
	}

	public FirebaseNotification(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public long getReceiverCategoryId() {
		return receiverCategoryId;
	}

	public void setReceiverCategoryId(long receiverCategoryId) {
		this.receiverCategoryId = receiverCategoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(long navigationId) {
		this.navigationId = navigationId;
	}

	public long getNavigationCategoryId() {
		return navigationCategoryId;
	}

	public void setNavigationCategoryId(long navigationCategoryId) {
		this.navigationCategoryId = navigationCategoryId;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
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

}
