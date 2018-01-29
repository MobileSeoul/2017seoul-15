package com.stm.market.fragment.story.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketStoryInteractor {
    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    List<Story> getStories();

    void setStories(List<Story> stories);

    void setFileRepository(String accessToken);

    void setFileRepository();

    void setStoryRepository(String accessToken);

    void setStoryRepository();

    void setMarketRepository(String accessToken);

    void setMarketRepository();

    void getStoryListByIdAndOffset(long id, long userId, long offset);

    void getStoryListByIdAndOffset(long id, long offset);

    void setStoriesAddAll(List<Story> newStories);

    void setStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long userId, int position);

    void replaceStoriesItemAtThePosition(int position, Story story);

    void setStoriesRemoveAtThePosition(int position);

    void updateFileByHits(File file);
}
