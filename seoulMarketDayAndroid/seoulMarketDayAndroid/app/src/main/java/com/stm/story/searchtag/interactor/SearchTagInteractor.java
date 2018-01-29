package com.stm.story.searchtag.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface SearchTagInteractor {
    User getUser();

    void setUser(User user);

    List<Story> getStories();

    void setStories(List<Story> stories);

    String getTagName();

    void setTagName(String tagName);

    void setStoriesAddAll(List<Story> stories);

    void setStoriesRemoveAtThePosition(int position);

    void setStoryTagRepository();

    void setStoryTagRepository(String accessToken);

    void setFileRepository();

    void setFileRepository(String accessToken);

    void setStoryRepository();

    void setStoryRepository(String accessToken);

    void getStoryListByTagNameAndOffset(String tagName, long offset);

    void getStoryListByTagNameAndUserIdAndOffset(String tagName, long userId, long offset);

    void updateFileByHits(File file);

    void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId, int position);

    void replaceStoriesItemAtThePosition(int position, Story story);
}
