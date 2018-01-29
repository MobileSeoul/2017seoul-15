package com.stm.user.detail.merchant.fragment.follower.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.detail.merchant.fragment.follower.interactor.MerchantDetailFollowerInteractor;
import com.stm.user.detail.merchant.fragment.follower.presenter.MerchantDetailFollowerPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-04.
 */

public class MerchantDetailFollowerInteractorImpl implements MerchantDetailFollowerInteractor {
    private MerchantDetailFollowerPresenter merchantDetailFollowerPresenter;
    private List<User> followers;

    private User storyUser;
    private User user;

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailFollowerInteractorImpl.class);


    public MerchantDetailFollowerInteractorImpl(MerchantDetailFollowerPresenter merchantDetailFollowerPresenter) {
        this.merchantDetailFollowerPresenter = merchantDetailFollowerPresenter;
        this.followers = new ArrayList<>();
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
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void getFollowerListByStoryUserIdAndOffset(long storyUserId, long offset) {
        Call<List<User>> CallFollowerListByStoryUserIdAndOffsetApi = userRepository.findFollowerListByStoryUserIdAndOffset(storyUserId, offset);
        CallFollowerListByStoryUserIdAndOffsetApi.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followers = response.body();
                    merchantDetailFollowerPresenter.onSuccessGetFollowerListByStoryUserIdAndOffset(followers);
                } else {
                    merchantDetailFollowerPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                log(t);
                merchantDetailFollowerPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    @Override
    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    @Override
    public void setFollowersAddAll(List<User> followers) {
        this.followers.addAll(followers);
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
