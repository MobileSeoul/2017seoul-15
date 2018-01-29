package com.stm.login.find.password.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.find.password.interactor.FindPasswordInteractor;
import com.stm.login.find.password.presenter.FindPasswordPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class FindPasswordInteractorImpl implements FindPasswordInteractor {
    private FindPasswordPresenter findPasswordPresenter;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(FindPasswordInteractorImpl.class);

    public FindPasswordInteractorImpl(FindPasswordPresenter findPasswordPresenter) {
        this.findPasswordPresenter = findPasswordPresenter;
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void getUserByEmail(String email) {
        Call<User> findGetUserByEmailApi = userRepository.findUserByEmail(email);
        findGetUserByEmailApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    findPasswordPresenter.onSuccessGetUserByEmail(user);
                } else {
                    findPasswordPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                findPasswordPresenter.onNetworkError(null);
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
