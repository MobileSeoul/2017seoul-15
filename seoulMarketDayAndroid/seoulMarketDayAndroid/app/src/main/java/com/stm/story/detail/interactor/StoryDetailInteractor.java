package com.stm.story.detail.interactor;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public interface StoryDetailInteractor {
    User getUser();

    void setUser(User user);

    Story getStory();

    void setStory(Story story);

    void setStoryRepository();

    void setStoryRepository(String accessToken);

    void getStoryById(long id);

    void getStoryById(long id, User user);

    void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName);

    void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId);
}
