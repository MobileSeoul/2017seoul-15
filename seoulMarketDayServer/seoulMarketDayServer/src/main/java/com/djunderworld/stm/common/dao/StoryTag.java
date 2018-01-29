package com.djunderworld.stm.common.dao;


/**
 * 
 * 스토리 해쉬태그 연결 정보 엔티티 클래스
 * 
 * 유저 정보, 해쉬태그 정보
 * 
 * @author dongjooKim
 */
public class StoryTag extends Base {
	private Story story;
	private String tagName;
	
	public StoryTag() {
		super();
	}

	public StoryTag(long id, String createdAt, String updatedAt) {
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

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
}
