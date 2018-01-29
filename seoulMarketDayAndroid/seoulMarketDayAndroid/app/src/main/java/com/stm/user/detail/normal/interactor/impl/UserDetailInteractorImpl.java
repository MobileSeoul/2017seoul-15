package com.stm.user.detail.normal.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.repository.remote.UserRepository;
import com.stm.user.detail.normal.interactor.UserDetailInteractor;
import com.stm.user.detail.normal.presenter.UserDetailPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class UserDetailInteractorImpl implements UserDetailInteractor {
    private UserDetailPresenter userDetailPresenter;
    private UserRepository userRepository;
    private User user;
    private User storyUser;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailInteractorImpl.class);

    public UserDetailInteractorImpl(UserDetailPresenter userDetailPresenter) {
        this.userDetailPresenter = userDetailPresenter;
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
    public void getUserById(long id) {
        Call<User> callFindUserByIdApi = userRepository.findUserById(id);
        callFindUserByIdApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    storyUser = response.body();
                    userDetailPresenter.onSuccessGetUserById(storyUser);
                } else {
                    userDetailPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                userDetailPresenter.onNetworkError(null);
            }
        });
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
