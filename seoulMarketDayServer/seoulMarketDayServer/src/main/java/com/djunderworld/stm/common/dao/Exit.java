package com.djunderworld.stm.common.dao;

/**
 * 
 * 유저 탈퇴 신청 정보 엔티티 클래스
 * 
 * 유저 정보, 탈퇴 사유 카테고리 정보 , 탈퇴 사유 내용
 * 
 * @author dongjooKim
 */
public class Exit extends Base {
	private User user;
	private ExitCategory exitCategory;
	private String content;

	public Exit() {
		super();
	}

	public Exit(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExitCategory getExitCategory() {
		return exitCategory;
	}

	public void setExitCategory(ExitCategory exitCategory) {
		this.exitCategory = exitCategory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
