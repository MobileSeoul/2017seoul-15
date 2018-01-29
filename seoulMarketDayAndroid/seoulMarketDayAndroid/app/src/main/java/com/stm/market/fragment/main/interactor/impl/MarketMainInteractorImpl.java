package com.stm.market.fragment.main.interactor.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.fragment.main.interactor.MarketMainInteractor;
import com.stm.market.fragment.main.presenter.MarketMainPresenter;
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
 * Created by Dev-0 on 2017-07-03.
 */

public class MarketMainInteractorImpl implements MarketMainInteractor {
    private MarketMainPresenter marketMainPresenter;
    private User user;
    private Market market;
    private List<Story> stories;
    private List<File> photos;
    private List<File> videos;
    private FileRepository fileRepository;
    private MarketRepository marketRepository;
    private StoryRepository storyRepository;

    private boolean isFirstCreated;
    private static final Logger logger = LoggerFactory.getLogger(MarketMainInteractorImpl.class);

    public MarketMainInteractorImpl(MarketMainPresenter marketMainPresenter) {
        this.marketMainPresenter = marketMainPresenter;
        this.stories = new ArrayList<>();
        this.photos = new ArrayList<>();
        this.videos = new ArrayList<>();

        this.isFirstCreated = false;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Market getMarket() {
        return this.market;
    }

    @Override
    public void setMarket(Market market) {
        this.market = market;
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
    public void setMarketRepository(String accessToken) {
        this.marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository() {
        this.marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
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
    public boolean isFirstCreated() {
        return isFirstCreated;
    }

    @Override
    public void setFirstCreated(boolean firstCreated) {
        isFirstCreated = firstCreated;
    }

    @Override
    public void setPhotosAddAll(List<File> newFiles) {
        this.photos.addAll(newFiles);
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
        Call<ResponseBody> callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.saveStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName);
        callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketMainPresenter.onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(position);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteStoryLikeByIdAndStoryUserIdAndUserId(long storyId, long userId, final int position) {
        Call<ResponseBody> callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi = storyRepository.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId);
        callSaveStoryLikeByIdAndStoryUserIdAndUserIdApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketMainPresenter.onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(position);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }


    @Override
    public void setVideosAddAll(List<File> newFiles) {
        this.videos.addAll(newFiles);
    }

    @Override
    public void getMarketById(long id, long userId) {
        Call<Market> callFindMarketByIdApi = marketRepository.findMarketById(id, userId);
        callFindMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketMainPresenter.onSuccessGetMarketById(market);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getMarketById(long id) {
        Call<Market> callFindMarketByIdApi = marketRepository.findMarketById(id);
        callFindMarketByIdApi.enqueue(new Callback<Market>() {
            @Override
            public void onResponse(Call<Market> call, Response<Market> response) {
                if (response.isSuccessful()) {
                    Market market = response.body();
                    marketMainPresenter.onSuccessGetMarketById(market);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Market> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getFileListByIdAndTypeAndOffset(long id, final int type, long offset) {
        Call<List<File>> callFindFileListByIdAndTypeAndOffsetApi = marketRepository.findFileListByIdAndTypeAndOffset(id, type, offset);
        callFindFileListByIdAndTypeAndOffsetApi.enqueue(new Callback<List<File>>() {
            @Override
            public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                if (response.isSuccessful()) {
                    List<File> files = response.body();
                    marketMainPresenter.onSuccessGetFileListByIdAndTypeAndOffset(files, type);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<File>> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });

    }

    @Override
    public void getStoryListByIdAndOffset(long id, long userId, long offset) {
        Call<List<Story>> callFindStoryListByIdAndTypeAndOffsetApi = marketRepository.findStoryListByIdAndOffset(id, userId, offset);
        callFindStoryListByIdAndTypeAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    marketMainPresenter.onSuccessGetStoryListByIdAndOffset(stories);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getStoryListByIdAndOffset(long id, long offset) {
        Call<List<Story>> callFindStoryListByIdAndTypeAndOffsetApi = marketRepository.findStoryListByIdAndOffset(id, offset);
        callFindStoryListByIdAndTypeAndOffsetApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    List<Story> stories = response.body();
                    marketMainPresenter.onSuccessGetStoryListByIdAndOffset(stories);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateFileByHits(final File file, final int position) {
        long fileId = file.getId();
        Call<ResponseBody> callUpdateFileByHitsApi = fileRepository.updateFileByHits(fileId);
        callUpdateFileByHitsApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketMainPresenter.onSuccessUpdateFileByHits(position);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
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
                    marketMainPresenter.onSuccessUpdateFileByHits(file);
                } else {
                    marketMainPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMainPresenter.onNetworkError(null);
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

