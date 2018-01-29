package com.djunderworld.stm.common.dao;

/**
 * 
 * 유저 행위 정보 엔티티 클래스
 * 
 * 유저 행위 정보(1.에 새로운 글을 등록하였습니다., 2.에 댓글을 남겼습니다., 3.게시글을 좋아합니다., 4.님을 팔로우합니다.)
 * 
 * @author dongjooKim
 */
public class Behavior extends Base {
	private String name;

	public Behavior() {
		super();
	}

	public Behavior(long id, String createdAt, String updatedAt) {
		super(id, createdAt, updatedAt);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
