package com.djunderworld.stm.common.dao;

/**
 * 
 * 고객 의견 정보 엔티티 클래스
 * 
 * 고객 정보, 고객 의견 카테고리 정보, 내용 정보
 * 
 * @author dongjooKim
 */
public class Opinion extends Base {

	private User user;
	private OpinionCategory opinionCategory;
	private String content;

	public Opinion() {
		super();
	}

	public Opinion(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OpinionCategory getOpinionCategory() {
		return opinionCategory;
	}

	public void setOpinionCategory(OpinionCategory opinionCategory) {
		this.opinionCategory = opinionCategory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
