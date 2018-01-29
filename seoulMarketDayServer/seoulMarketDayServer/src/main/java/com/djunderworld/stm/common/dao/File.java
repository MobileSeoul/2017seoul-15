package com.djunderworld.stm.common.dao;


/**
 * 
 * 파일 정보 엔티티 클래스
 * 
 * 스토리 정보,파일 게시 정보(1.게시글, 2.댓글) 파일 유형(1.사진, 2.동영상, 3.VR360), 파일 이름, 확장자, 파일 크기
 * 
 * @author dongjooKim
 */
public class File extends Base  {
	private Story story;
	private User user;
	
	private int postCategoryId;
	private int type;
	private String name;
	private String ext;
	private long size;
	private int hits;
	
	public File() {
		super();
	}

	public File(long id, String createdAt, String updatedAt) {
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

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getPostCategoryId() {
		return postCategoryId;
	}

	public void setPostCategoryId(int postCategoryId) {
		this.postCategoryId = postCategoryId;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
