package com.stm.main.fragment.main.search.fragment.tag.interactor.impl;

import android.util.Log;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.main.fragment.main.search.fragment.tag.interactor.SearchTagInteractor;
import com.stm.main.fragment.main.search.fragment.tag.presenter.SearchTagPresenter;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.StoryTagRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchTagInteractorImpl implements SearchTagInteractor {
    private SearchTagPresenter searchTagPresenter;
    private User user;
    private String keyword;
    private List<Story> stories;
    private StoryTagRepository storyTagRepository;
    private StoryRepository storyRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchTagInteractorImpl.class);
    private FileRepository fileRepository;

    public SearchTagInteractorImpl(SearchTagPresenter searchTagPresenter) {
        this.searchTagPresenter = searchTagPresenter;
        this.stories = new ArrayList<>();
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
    public String getKeyword() {
        return keyword;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    public void setStoryTagRepository(String accessToken) {
        this.storyTagRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryTagRepository().create(StoryTagRepository.class);
    }

    @Override
    public void setStoryTagRepository() {
        this.storyTagRepository = new NetworkInterceptor().getRetrofitForStoryTagRepository().create(StoryTagRepository.class);
    }

    @Override
    public void setStoryRepository(String accessToken) {
        this.storyRepository = new NetworkInterceptor(accessToken).getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setStoryRepository() {
        this.storyRepository = new NetworkInterceptor().getRetrofitForStoryRepository().create(StoryRepository.class);
    }

    @Override
    public void setFileRepository() {
        this.fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFileRepository(String accessToken) {
        this.fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setStoriesAddAll(List<Story> stories) {
        this.stories.addAll(stories);
    }

    @Override
    public void setStoriesRemoveAtThePosition(int position) {
        this.stories.remove(position);
    }

    @Override
    public void replaceStoriesItemAtThePosition(int position, Story story){
        this.stories.set(position, story);
    }

    @Override
    public void getStoryListByTagNameAndOffset(final String keyword, long userId, long offset) {
        Call<List<Story>> callFindStoryListByTagNameAndOffsetApi = storyTagRepository.findStoryListByTagNameAndOffset(keyword, userId, offset);
        callFindStoryListByTagNameAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    searchTagPresenter.onSuccessGetStoryListByTagNameAndOffset(stories, keyword);
                } else {
                    searchTagPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                searchTagPresenter.onNetworkError(null);

            }
        });
    }

    @Override
    public void getStoryListByTagNameAndOffset(final String keyword, long offset) {
        Call<List<Story>> callFindStoryListByTagNameAndOffsetApi = storyTagRepository.findStoryListByTagNameAndOffset(keyword, offset);
        callFindStoryListByTagNameAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    searchTagPresenter.onSuccessGetStoryListByTagNameAndOffset(stories, keyword);
                } else {
                    searchTagPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                searchTagPresenter.onNetworkError(null);

            }
        });
    }

    @Override
    public void setStoryLikeByStoryIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, final int position) {
        Call<ResponseBody> callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.saveStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
        callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    searchTagPresenter.onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(position);
                } else {
                    searchTagPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                searchTagPresenter.onNetworkError(null);
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
                    searchTagPresenter.onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(position);
                } else {
                    searchTagPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                searchTagPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateFileByHits(final File file) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    searchTagPresenter.onSuccessUpdateFileByHits(file);
                } else {
                    searchTagPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                searchTagPresenter.onNetworkError(null);
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

