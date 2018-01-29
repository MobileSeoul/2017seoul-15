package com.djunderworld.stm.common.dao;

/**
 * 
 * 고객 의견 카테고리 정보 엔티티 클래스
 * 
 * 고객 의견 카테고리 이름정보(1.게시글, 2.사진, 3.동영상, 4.페이지, 5.검색, 6.공개범위, 7.광고, 8.로그인, 9.이벤트, 10.댓글, 11.위치, 12.기타)
 * 
 * @author dongjooKim
 */
public class OpinionCategory extends Base {
	private String name;

	public OpinionCategory() {
		super();
	}

	public OpinionCategory(long id, String createdAt, String updatedAt) {
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
