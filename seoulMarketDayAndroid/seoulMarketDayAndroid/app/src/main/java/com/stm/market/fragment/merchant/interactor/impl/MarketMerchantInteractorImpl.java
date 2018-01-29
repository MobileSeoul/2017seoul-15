package com.stm.market.fragment.merchant.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.market.fragment.merchant.interactor.MarketMerchantInteractor;
import com.stm.market.fragment.merchant.presenter.MarketMerchantPresenter;
import com.stm.repository.remote.MarketRepository;
import com.stm.repository.remote.UserRepository;
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
 * Created by Dev-0 on 2017-07-06.
 */

public class MarketMerchantInteractorImpl implements MarketMerchantInteractor {
    private MarketMerchantPresenter marketMerchantPresenter;
    private User user;
    private Market market;
    private List<User> users;
    private MarketRepository marketRepository;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MarketMerchantInteractorImpl.class);

    public MarketMerchantInteractorImpl(MarketMerchantPresenter marketMerchantPresenter) {
        this.marketMerchantPresenter = marketMerchantPresenter;
        this.users = new ArrayList<>();
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
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void getUserListByIdAndOffset(long marketId, long offset) {
        Call<List<User>> callGetUserListByIdAndOffsetApi = marketRepository.findUserListByIdAndOffset(marketId, offset);
        callGetUserListByIdAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    marketMerchantPresenter.onSuccessGetUserListByIdAndOffset(users);
                } else {
                    marketMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));


                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                marketMerchantPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void getUserListByIdAndUserIdAndOffset(long marketId, long userId, long offset) {
        Call<List<User>> callGetUserListByIdAndOffsetApi = marketRepository.findUserListByIdAndUserIdAndOffset(marketId, userId, offset);
        callGetUserListByIdAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    marketMerchantPresenter.onSuccessGetUserListByIdAndOffset(users);
                } else {
                    marketMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                marketMerchantPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setUsersAddAll(List<User> users) {
        this.users.addAll(users);
    }

    @Override
    public void setMerchantFollower(MerchantFollower merchantFollower, final int position) {
        User storyUser = merchantFollower.getMerchant();
        long storyUserId = storyUser.getId();

        Call<ResponseBody> CallSetMerchantFollowerApi = userRepository.saveMerchantFollower(storyUserId, merchantFollower);
        CallSetMerchantFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketMerchantPresenter.onSuccessSetMerchantFollower(position);
                } else {
                    marketMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMerchantPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void deleteMerchantFollower(MerchantFollower merchantFollower, final int position) {
        User storyUser = merchantFollower.getMerchant();
        long storyUserId = storyUser.getId();

        Call<ResponseBody> CallDeleteMerchantFollowerApi = userRepository.deleteMerchantFollower(storyUserId, merchantFollower);
        CallDeleteMerchantFollowerApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    marketMerchantPresenter.onSuccessDeleteMerchantFollower(position);
                } else {
                    marketMerchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                marketMerchantPresenter.onNetworkError(null);
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
