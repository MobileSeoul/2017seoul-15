package com.stm.main.fragment.user.base.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.main.fragment.user.base.interactor.UserInteractor;
import com.stm.main.fragment.user.base.presenter.UserPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class UserInteractorImpl implements UserInteractor {
    private UserPresenter userPresenter;
    private User user;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserInteractorImpl.class);

    public UserInteractorImpl(UserPresenter userPresenter) {
        this.userPresenter = userPresenter;
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
    public void setUserRepository(String accessToken) {
        this.userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void updateUser(User user) {
        long userId = user.getId();
        Call<User> callUpdateUserApi = userRepository.updateUser(userId, user);
        callUpdateUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                userPresenter.onSuccessUpdateUser();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                userPresenter.onNetworkError(null);
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
