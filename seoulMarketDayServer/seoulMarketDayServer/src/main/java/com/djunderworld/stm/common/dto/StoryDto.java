package com.djunderworld.stm.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.djunderworld.stm.common.dao.File;
import com.djunderworld.stm.common.dao.Story;

/**
 * 스토리 Data Transfer Object 클래스
 * 
 * 스토리 정보, 지우려는 파일들 정보
 * 
 * @author dongjooKim
 */
public class StoryDto {
	private Story story;
	private List<File> removeFiles = new ArrayList<File>();

	public StoryDto() {
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public List<File> getRemoveFiles() {
		return removeFiles;
	}

	public void setRemoveFiles(List<File> removeFiles) {
		this.removeFiles = removeFiles;
	}
}
