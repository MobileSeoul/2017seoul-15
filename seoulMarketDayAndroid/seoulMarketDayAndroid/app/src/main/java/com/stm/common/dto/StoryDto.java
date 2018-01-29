package com.stm.common.dto;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class StoryDto {
    private Story story;
    private List<File> removeFiles = new ArrayList<>();

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
