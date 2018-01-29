package com.stm.main.fragment.main.search.fragment.tag.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchTagInteractor {
    User getUser();

    void setUser(User user);

    String getKeyword();

    void setKeyword(String keyword);

    List<Story> getStories();

    void setStories(List<Story> stories);

    void setStoryTagRepository(String accessToken);

    void setStoryTagRepository();

    void setStoryRepository(String accessToken);

    void setStoryRepository();

    void setFileRepository();

    void setFileRepository(String accessToken);

    void setStoriesAddAll(List<Story> stories);

    void replaceStoriesItemAtThePosition(int position, Story story);

    void getStoryListByTagNameAndOffset(String keyword, long userId, long offset);

    void getStoryListByTagNameAndOffset(String keyword, long offset);

    void setStoriesRemoveAtThePosition(int position);

    void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId, int position);

    void updateFileByHits(File file);

}
