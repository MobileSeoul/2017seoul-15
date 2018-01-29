package com.stm.market.fragment.main.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by Dev-0 on 2017-07-03.
 */

public interface MarketMainInteractor {

    User getUser();

    void setUser(User user);

    Market getMarket();

    void setMarket(Market market);

    List<File> getPhotos();

    void setPhotos(List<File> photos);

    List<File> getVideos();

    void setVideos(List<File> videos);

    List<Story> getStories();

    void setStories(List<Story> stories);

    void setFileRepository(String accessToken);

    void setFileRepository();

    void setMarketRepository(String accessToken);

    void setMarketRepository();

    void setVideosAddAll(List<File> newFiles);

    void getMarketById(long id, long userId);

    void getMarketById(long id);

    void getFileListByIdAndTypeAndOffset(long id, int type, long offset);

    void getStoryListByIdAndOffset(long id, long userId, long offset);

    void getStoryListByIdAndOffset(long id, long offset);

    void updateFileByHits(File file, int position);

    void updateFileByHits(File file);

    void setStoryRepository();

    void setStoryRepository(String accessToken);

    boolean isFirstCreated();

    void setFirstCreated(boolean firstCreated);

    void setPhotosAddAll(List<File> newFiles);

    void setStoriesAddAll(List<Story> newStories);

    void replaceStoriesItemAtThePosition(int position, Story story);

    void setStoriesRemoveAtThePosition(int position);

    void setStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long userId, int position);


}
