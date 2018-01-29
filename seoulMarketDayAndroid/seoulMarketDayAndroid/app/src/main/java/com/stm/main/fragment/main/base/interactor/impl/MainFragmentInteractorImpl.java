package com.stm.main.fragment.main.base.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.LocationDto;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.main.fragment.main.base.interactor.MainFragmentInteractor;
import com.stm.main.fragment.main.base.presenter.MainFragmentPresenter;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.StoryRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-06-14.
 */

public class MainFragmentInteractorImpl implements MainFragmentInteractor {
    private MainFragmentPresenter mainFragmentPresenter;

    private LocationDto locationDto;
    private List<Market> markets;
    private List<User> best5Users;
    private List<Story> best5Stories;

    private MarketRepository marketRepository;
    private UserRepository userRepository;
    private StoryRepository storyRepository;
    private static final Logger logger = LoggerFactory.getLogger(MainFragmentInteractorImpl.class);

    public MainFragmentInteractorImpl(MainFragmentPresenter mainFragmentPresenter) {
        this.mainFragmentPresenter = mainFragmentPresenter;
    }

    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    @Override
    public void setMarketRepository() {
        marketRepository = new NetworkInterceptor().getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketRepository(String accessToken) {
        marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
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
    public void getMarketListByLocation(LocationDto locationDto) {
        double latitude = locationDto.getLatitude();
        double longitude = locationDto.getLongitude();

        HashMap<String, Double> map = new HashMap<>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        Call<List<Market>> callGetMarketListByLocationApi = marketRepository.findMarketListByLocation(map);
        callGetMarketListByLocationApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    mainFragmentPresenter.onSuccessGetMarketListByLocation(markets);
                } else {
                    mainFragmentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                mainFragmentPresenter.onNetworkError(null);
            }
        });

    }

    @Override
    public void getMarketListByFollower() {
        Call<List<Market>> callGetMarketListByFollowerApi = marketRepository.findMarketListByFollower();
        callGetMarketListByFollowerApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    markets = response.body();
                    mainFragmentPresenter.onSuccessGetMarketListByFollower(markets);
                } else {
                    mainFragmentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                mainFragmentPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getBest5UserListPerMonth() {
        Call<List<User>> callGetBest5UserListPerMonthApi = userRepository.findBestMerchantListPerMonthLimitFive();
        callGetBest5UserListPerMonthApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    best5Users = response.body();
                    mainFragmentPresenter.onSuccessGetBest5UserListPerMonth(best5Users);
                } else {
                    mainFragmentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                mainFragmentPresenter.onNetworkError(null);
            }
        });
    }


    @Override
    public void getBest5StoryListPerMonth() {
        Call<List<Story>> callGetBest5StoryListPerMonthApi = storyRepository.findBestStoryListPerMonthLimitFive();
        callGetBest5StoryListPerMonthApi.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    best5Stories = response.body();
                    mainFragmentPresenter.onSuccessGetBest5StoryListPerMonth(best5Stories);
                } else {
                    mainFragmentPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                log(t);
                mainFragmentPresenter.onNetworkError(null);
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
