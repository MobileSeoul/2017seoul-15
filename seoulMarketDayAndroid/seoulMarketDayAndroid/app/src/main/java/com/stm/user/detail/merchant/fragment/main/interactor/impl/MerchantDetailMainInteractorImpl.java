package com.stm.user.detail.merchant.fragment.main.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.detail.merchant.fragment.main.interactor.MerchantDetailMainInteractor;
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class MerchantDetailMainInteractorImpl implements MerchantDetailMainInteractor {
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private User user;
    private User storyUser;
    private List<Story> stories;
    private boolean isFirstCreated;

    private List<File> photos;
    private List<File> videos;


    private StoryRepository storyRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailMainInteractorImpl.class);


    public MerchantDetailMainInteractorImpl(MerchantDetailMainPresenter merchantDetailMainPresenter) {
        this.merchantDetailMainPresenter = merchantDetailMainPresenter;
        this.stories = new ArrayList<>();
        this.photos = new ArrayList<>();
        this.videos = new ArrayList<>();

        this.isFirstCreated = false;
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setStoryRepository(String accessToken) {
        storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setStoryRepository() {
        storyRepository = new NetworkInterceptor().getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setFileRepository() {
        fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFileRepository(String accessToken) {
        fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getStoryUser() {
        return storyUser;
    }

    @Override
    public void setStoryUser(User storyUser) {
        this.storyUser = storyUser;
    }


    @Override
    public List<Story> getStories() {
        return stories;
    }

    @Override
    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    @Override
    public List<File> getPhotos() {
        return photos;
    }

    @Override
    public void setPhotos(List<File> photos) {
        this.photos = photos;
    }

    @Override
    public List<File> getVideos() {
        return videos;
    }

    @Override
    public void setVideos(List<File> videos) {
        this.videos = videos;
    }

    @Override
    public void setStoriesAddAll(List<Story> stories) {
        this.stories.addAll(stories);
    }

    @Override
    public void setStoriesClear() {
        this.stories.clear();
    }

    @Override
    public boolean isFirstCreated() {
        return isFirstCreated;
    }

    @Override
    public void setFirstCreated(boolean firstCreated) {
        isFirstCreated = firstCreated;
    }

    @Override
    public void replaceStoriesItemAtThePosition(int position, Story story) {
        this.stories.set(position, story);
    }

    @Override
    public void setPhotosAddAll(List<File> photos) {
        this.photos.addAll(photos);
    }

    @Override
    public void setVideosAddAll(List<File> videos) {
        this.videos.addAll(videos);
    }

    @Override
    public void setStoriesRemoveAtThePosition(int position) {
        this.stories.remove(position);
    }

    @Override
    public void getStoryListByStoryUserIdAndOffset(long storyUserId, long offset) {
        Call<List<Story>> callFindStoryListByStoryUserIdAndOffsetApi = userRepository.findStoryListByStoryUserIdAndOffset(storyUserId, offset);
        callFindStoryListByStoryUserIdAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    merchantDetailMainPresenter.onSuccessGetStoryListByStoryUserIdAndOffset(stories);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getStoryListByStoryUserIdAndUserIdAndOffset(long storyUserId, long userId, long offset) {
        Call<List<Story>> callFindStoryListByStoryUserIdAndUserIdAndOffsetApi = userRepository.findStoryListByStoryUserIdAndUserIdAndOffset(storyUserId, userId, offset);
        callFindStoryListByStoryUserIdAndUserIdAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    merchantDetailMainPresenter.onSuccessGetStoryListByStoryUserIdAndOffset(stories);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getFileListByIdAndTypeAndOffset(long storyUserId, final int type, long offset) {
        Call<List<File>> callFindStoryListByStoryUserIdAndUserIdAndOffsetApi = userRepository.findFileListByIdAndTypeAndOffset(storyUserId, type, offset);
        callFindStoryListByStoryUserIdAndUserIdAndOffsetApi.enqueue(new Callback<List<File>>() {
            @Override
            public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                if (response.isSuccessful()) {
                    List<File> files = response.body();
                    merchantDetailMainPresenter.onSuccessGetFileListByIdAndTypeAndOffset(files, type);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<File>> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, final int position) {
        Call<ResponseBody> callSaveStoryLikeByStoryIdAndStoryUserIdAndUserIdApi = storyRepository.saveStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
        callSaveStoryLikeByStoryIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailMainPresenter.onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(position);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId, final int position) {
        Call<ResponseBody> callDeleteStoryLikeByStoryIdAndStoryUserIdAndUserIdApi = storyRepository.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId);
        callDeleteStoryLikeByStoryIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailMainPresenter.onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(position);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateFileByHits(File file, final int position) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHits = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHits.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailMainPresenter.onSuccessUpdateFileByHits(position);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateFileByHits(final File file) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHits = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHits.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    merchantDetailMainPresenter.onSuccessUpdateFileByHits(file);
                } else {
                    merchantDetailMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                merchantDetailMainPresenter.onNetworkError(null);
            }
        });
    }

    private static void log(Throwable throwable) {
        StackTraceElement[] ste = throwable.getStackTrace();
        String className = ste[0].getClassName();
        String methodName = ste[0].getMethodName();
        int lineNumber = ste[0].getLineNumber();
        String fileName = ste[0].getFileName();

        if (LogFlag.printFlag) {
            if (logger.isInfoEnabled()) {
                logger.error("Exception: " + throwable.getMessage());
                logger.error(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
            }
        }
    }

}
