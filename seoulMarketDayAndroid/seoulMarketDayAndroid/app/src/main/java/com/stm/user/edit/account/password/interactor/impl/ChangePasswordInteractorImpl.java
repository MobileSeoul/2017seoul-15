package com.stm.user.edit.account.password.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.edit.account.password.interactor.ChangePasswordInteractor;
import com.stm.user.edit.account.password.presenter.ChangePasswordPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class ChangePasswordInteractorImpl implements ChangePasswordInteractor {
    private ChangePasswordPresenter changePasswordPresenter;
    private User user;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordInteractorImpl.class);

    public ChangePasswordInteractorImpl(ChangePasswordPresenter changePasswordPresenter) {
        this.changePasswordPresenter = changePasswordPresenter;
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
    public void getUserByEmailAndPassword(String email, final String encryptedCurrentPassword, final String newPassword) {
        Call<User> callUserByEmailAndPasswordApi = userRepository.findUserByEmailAndPassword(email, encryptedCurrentPassword);
        callUserByEmailAndPasswordApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    changePasswordPresenter.onSuccessGetUserByEmailAndPassword(user, encryptedCurrentPassword, newPassword);
                } else {
                    changePasswordPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                changePasswordPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void updateUser(long userId, User user){
        Call<User> callUpdateUserApi = userRepository.updateUser(userId, user);
        callUpdateUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    changePasswordPresenter.onSuccessUpdateUser(user);
                } else {
                    changePasswordPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                changePasswordPresenter.onNetworkError(null);
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
