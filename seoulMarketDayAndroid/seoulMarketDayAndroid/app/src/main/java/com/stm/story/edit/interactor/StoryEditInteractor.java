package com.stm.story.edit.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;
import com.stm.common.dto.StoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface StoryEditInteractor {
    void setStoryRepository(String accessToken);

    boolean isEdited();

    void setEdited(boolean edited);

    User getUser();

    void setUser(User user);

    Story getStory();

    void setStory(Story story);


    ArrayList<FileDto> getFileDtos();

    void setFileDtos(ArrayList<FileDto> fileDtos);

    void setRemoveFilesAdd(File file);

    void setFilesAdd(FileDto fileDto);

    void setFileDtosAddAll(ArrayList<FileDto> fileDtos);

    void setFileDtosAdd(FileDto fileDto);

    void setFileDtosRemoveAtThePosition(int position);

    List<File> getRemoveFiles();

    void setRemoveFiles(List<File> removeFiles);

    int getPrevFileSize();

    void setPrevFileSize(int prevFileSize);

    void getStoryByUploadingStoryDtoAndFileDtos(StoryDto storyDto, ArrayList<FileDto> fIleDtos);

    void getStoryByUploadingStoryDto(StoryDto storyDto);
}
