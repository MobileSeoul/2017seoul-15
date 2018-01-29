package com.stm.market.fragment.story.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.fragment.story.interactor.MarketStoryInteractor;
import com.stm.market.fragment.story.presenter.MarketStoryPresenter;
import com.stm.repository.remote.FileRepository;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public class MarketStoryInteractorImpl implements MarketStoryInteractor {
    private MarketStoryPresenter marketStoryPresenter;
    private User user;
    private Market market;
    private List<Story> stories;
    private StoryRepository storyRepository;
    private FileRepository fileRepository;
    private MarketRepository marketRepository;
    private static final Logger logger = LoggerFactory.getLogger(MarketStoryInteractorImpl.class);

    public MarketStoryInteractorImpl(MarketStoryPresenter marketStoryPresenter) {
        this.marketStoryPresenter = marketStoryPresenter;
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
    public Market getMarket() {
        return market;
    }

    @Override
    public void setMarket(Market market) {
        this.market = market;
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
    public void setFileRepository(String accessToken) {
        this.fileRepository = new NetworkInterceptor(accessToken).getRetrofitForFileRepository().create(FileRepository.class);
    }

    @Override
    public void setFileRepository() {
        this.fileRepository = new NetworkInterceptor().getRetrofitForFileRepository().create(FileRepository.class);
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
    public void setMarketRepository(String accessToken) {
        this.marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository() {
        this.marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void getStoryListByIdAndOffset(long id, long userId, long offset) {
        Call<List<Story>> callFindStoryListByIdAndOffsetApi = marketRepository.findStoryListByIdAndOffset(id, userId, offset);
        callFindStoryListByIdAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    marketStoryPresenter.onSuccessGetStoryListByIdAndOffset(stories);
                } else {
                    marketStoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                marketStoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getStoryListByIdAndOffset(long id, long offset) {
        Call<List<Story>> callFindStoryListByIdAndOffsetApi = marketRepository.findStoryListByIdAndOffset(id, offset);
        callFindStoryListByIdAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    marketStoryPresenter.onSuccessGetStoryListByIdAndOffset(stories);
                } else {
                    marketStoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                marketStoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setStoriesAddAll(List<Story> newStories) {
        this.stories.addAll(newStories);
    }

    @Override
    public void replaceStoriesItemAtThePosition(int position, Story story) {
        this.stories.set(position, story);
    }

    @Override
    public void setStoriesRemoveAtThePosition(int position) {
        this.stories.remove(position);
    }

    @Override
    public void setStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long storyUserId, long userId, String userName, final int position) {
        Call<ResponseBody> callFindStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.saveStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
        callFindStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketStoryPresenter.onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(position);
                } else {
                    marketStoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketStoryPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long userId, final int position) {
        Call<ResponseBody> callDeleteStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId);
        callDeleteStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketStoryPresenter.onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(position);
                } else {
                    marketStoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketStoryPresenter.onNetworkError(null);
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
                    marketStoryPresenter.onSuccessUpdateFileByHits(file);
                } else {
                    marketStoryPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketStoryPresenter.onNetworkError(null);
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
