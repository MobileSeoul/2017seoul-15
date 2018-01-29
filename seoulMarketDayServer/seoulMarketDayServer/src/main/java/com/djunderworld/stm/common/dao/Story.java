package com.djunderworld.stm.common.dao;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * 스토리 정보 엔티티 클래스
 * 
 * 유저 정보, 게시글 글 내용, 파일 정보 리스트, 좋아요 수, 댓글 수, 좋아요 체크여부, 스토리 해쉬태그 정보
 * 
 * @author dongjooKim
 */

@JsonIgnoreProperties(value = {"handler"})
public class Story extends Base {
	private User user = new User();
	private String content;

	private int likeCount;
	private int commentCount;
	private Boolean isLikeChecked;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private List<File> files = new ArrayList<File>();

	private List<StoryTag> storyTags = new ArrayList<StoryTag>();

	public Story() {
		super();
	}

	public Story(long id, String createdAt, String updatedAt) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<StoryTag> getStoryTags() {
		return storyTags;
	}

	public void setStoryTags(List<StoryTag> storyTags) {
		this.storyTags = storyTags;
	}

	public Boolean getIsLikeChecked() {
		return isLikeChecked;
	}

	public void setIsLikeChecked(Boolean isLikeChecked) {
		this.isLikeChecked = isLikeChecked;
	}

}
