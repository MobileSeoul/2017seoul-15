package com.stm.user.detail.merchant.fragment.story.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-28.
 */

public interface MerchantDetailStoryInteractor {
    void setUserRepository(String accessToken);

    void setUserRepository();

    void setFileRepository();

    void setFileRepository(String accessToken);

    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);

    List<Story> getStories();

    void setStories(List<Story> stories);


    void updateFileByHits(File file);

    void getStoryListByStoryUserIdAndUserIdAndOffset(long storyUserId, long userId, long offset);

    void getStoryListByStoryUserIdAndOffset(long storyUserId, long offset);


    void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId, int position);

    void setStoriesAddAll(List<Story> newStories);

    void setStoryRepository(String accessToken);

    void setStoryRepository();

    void setStoriesRemoveAtThePosition(int position);

    void setStoriesRemoveAll();

    void replaceStoriesItemAtThePosition(int position, Story story);
}
