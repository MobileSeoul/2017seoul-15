package com.stm.story.detail.interactor.impl;

import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.story.detail.interactor.StoryDetailInteractor;
import com.stm.story.detail.presenter.StoryDetailPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public class StoryDetailInteractorImpl implements StoryDetailInteractor {
    private StoryDetailPresenter storyDetailPresenter;
    private User user;
    private Story story;
    private StoryRepository storyRepository;
    private static final Logger logger = LoggerFactory.getLogger(StoryDetailInteractorImpl.class);

    public StoryDetailInteractorImpl(StoryDetailPresenter storyDetailPresenter) {
        this.storyDetailPresenter = storyDetailPresenter;
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
    public Story getStory() {
        return story;
    }

    @Override
    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public void setStoryRepository() {
        this.storyRepository = new NetworkInterceptor().getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setStoryRepository(String accessToken) {
        this.storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void getStoryById(long id) {
        Call<Story> callFindStoryByIdApi = storyRepository.findStoryById(id);
        callFindStoryByIdApi.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    Story story = response.body();
                    storyDetailPresenter.onSuccessGetStoryById(story);
                } else {
                    storyDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                log(t);
                storyDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getStoryById(long id, User user) {
        long userId = user.getId();
        Call<Story> callFindStoryByIdApi = storyRepository.findStoryById(id, userId);
        callFindStoryByIdApi.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    Story story = response.body();
                    storyDetailPresenter.onSuccessGetStoryById(story);
                } else {
                    storyDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                log(t);
                storyDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName) {
        Call<ResponseBody> callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.saveStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
        callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    storyDetailPresenter.onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId();
                } else {
                    storyDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                storyDetailPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long userId) {
        Call<ResponseBody> callDeleteStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId);
        callDeleteStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    storyDetailPresenter.onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId();
                } else {
                    storyDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                storyDetailPresenter.onNetworkError(null);
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
