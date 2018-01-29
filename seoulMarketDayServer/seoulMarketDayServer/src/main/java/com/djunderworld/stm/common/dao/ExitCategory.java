package com.djunderworld.stm.common.dao;

/**
 * 
 * 유저 탈퇴 사유 카테고리 정보 엔티티 클래스
 * 
 * 탈퇴 사유 카테고리 정보(1.앱의 잦은 장애, 2.적은 사용빈도, 3.찾으려는 정보부재, 4.개인정보유출 우려, 5.기타)
 * 
 * @author dongjooKim
 */
public class ExitCategory extends Base {
	private String name;

	public ExitCategory() {
		super();
	}

	public ExitCategory(long id, String createdAt, String updatedAt) {
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
