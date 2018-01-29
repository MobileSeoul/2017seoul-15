package com.stm.user.detail.merchant.fragment.main.interactor;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailMainInteractor {

    void setUserRepository(String accessToken);

    void setUserRepository();

    void setStoryRepository(String accessToken);

    void setStoryRepository();

    void setFileRepository();

    void setFileRepository(String accessToken);

    User getUser();

    void setUser(User user);

    User getStoryUser();

    void setStoryUser(User storyUser);


    List<Story> getStories();

    void setStories(List<Story> stories);

    void setStoriesClear();

    boolean isFirstCreated();

    void setFirstCreated(boolean firstCreated);

    void replaceStoriesItemAtThePosition(int position, Story story);

    void setPhotosAddAll(List<File> photos);

    void setVideosAddAll(List<File> videos);

    void setStoriesRemoveAtThePosition(int position);

    void getStoryListByStoryUserIdAndOffset(long storyUserId, long offset);

    void getStoryListByStoryUserIdAndUserIdAndOffset(long storyUserId, long userId, long offset);


    List<File> getPhotos();

    void setPhotos(List<File> photos);

    List<File> getVideos();

    void setVideos(List<File> videos);


    void setStoriesAddAll(List<Story> stories);

    void getFileListByIdAndTypeAndOffset(long storyUserId, int type, long offset);

    void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, int position);

    void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId, int position);

    void updateFileByHits(File file, int position);

    void updateFileByHits(File file);
}
