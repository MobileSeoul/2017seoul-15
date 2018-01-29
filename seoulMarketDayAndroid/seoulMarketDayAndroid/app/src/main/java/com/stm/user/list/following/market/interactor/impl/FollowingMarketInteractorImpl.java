package com.stm.user.list.following.market.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.list.following.market.interactor.FollowingMarketInteractor;
import com.stm.user.list.following.market.presenter.FollowingMarketPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-30.
 */

public class FollowingMarketInteractorImpl implements FollowingMarketInteractor {
    private FollowingMarketPresenter followingMarketPresenter;
    private User user;
    private List<Market> markets;
    private UserRepository userRepository;
    private MarketRepository marketRepository;
    private static final Logger logger = LoggerFactory.getLogger(FollowingMarketInteractorImpl.class);

    public FollowingMarketInteractorImpl(FollowingMarketPresenter followingMarketPresenter) {
        this.followingMarketPresenter = followingMarketPresenter;
        this.markets = new ArrayList<>();
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
    public List<Market> getMarkets() {
        return markets;
    }

    @Override
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    @Override
    public void setUserRepository(String accessToken) {
        this.userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setMarketRepository(String accessToken) {
        this.marketRepository = new NetworkInterceptor(accessToken).getRetrofitForMarketRepository().create(MarketRepository.class);
    }

    @Override
    public void setMarketAddAll(List<Market> markets) {
        this.markets.addAll(markets);
    }

    @Override
    public void getFollowingMarketListByIdAndOffset(long userId, long offset) {
        Call<List<Market>> callFindFollowingMarketListByIdAndOffsetApi = userRepository.findFollowingMarketListByIdAndOffset(userId, offset);
        callFindFollowingMarketListByIdAndOffsetApi.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()) {
                    List<Market> markets = response.body();
                    followingMarketPresenter.onSuccessGetFollowingMarketListByIdAndOffset(markets);
                } else {
                    followingMarketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                log(t);
                followingMarketPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteMarketFollower(long marketId, MarketFollower marketFollower, final int position) {
        Call<ResponseBody> callDeleteMarketFollower = marketRepository.deleteMarketFollower(marketId, marketFollower);
        callDeleteMarketFollower.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    followingMarketPresenter.onSuccessDeleteMarketFollower(position);
                } else {
                    followingMarketPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                followingMarketPresenter.onNetworkError(null);
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
