package com.djunderworld.stm.common.dao;


/**
 * 
 * 공개범위 정보 엔티티 클래스
 * 
 * 공개 분류 이름정보(1.전체공개, 2.비공개)
 * 
 * @author dongjooKim
 */
public class RangeCategory  extends Base  {
	private String name;

	public RangeCategory() {
		super();
	}

	public RangeCategory(long id, String createdAt, String updatedAt) {
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
