package com.stm.story.create.interactor;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.FileDto;

import java.util.ArrayList;

/**
 * Created by ㅇㅇ on 2017-07-11.
 */

public interface StoryCreateInteractor {
    void setStoryRepository(String accessToken);

    User getUser();

    void setUser(User user);

    void setFileDtosAdd(FileDto fileDto);

    void setFileDtosAddAll(ArrayList<FileDto> fileDtos);

    ArrayList<FileDto> getFileDtos();

    void setFileDtos(ArrayList<FileDto> fileDtos);


    void getStoryIdByUploadingStoryAndFileDtos(Story story, ArrayList<FileDto> fIleDtos);

    void getStoryIdByUploadingStory(Story story);
}
