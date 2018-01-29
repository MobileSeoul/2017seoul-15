package com.djunderworld.stm.common.dao;
/**
*
* 신고 정보 엔티티 클래스
* 
* 신고자 정보, 신고 컨텐츠 유형(1.스토리, 2.댓글), 신고 컨텐츠 아이디
* 
* @author dongjooKim
*/
public class Report extends Base {
	private User user;
	private long contentCategoryId;
	private Long contentId;

	public Report() {
		super();
	}

	public Report(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getContentCategoryId() {
		return contentCategoryId;
	}

	public void setContentCategoryId(long contentCategoryId) {
		this.contentCategoryId = contentCategoryId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
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
