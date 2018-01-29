package com.stm.login.create.join.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.create.join.interactor.JoinInteractor;
import com.stm.login.create.join.presenter.JoinPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-06-21.
 */

public class JoinInteractorImpl implements JoinInteractor {
    private JoinPresenter joinPresenter;
    private Market market;
    private UserRepository userRepository;
    private User user;
    private static final Logger logger = LoggerFactory.getLogger(JoinInteractorImpl.class);

    public JoinInteractorImpl(JoinPresenter joinPresenter) {
        this.joinPresenter = joinPresenter;
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
    }

    @Override
    public void getCheckByEmail(String email) {
        Call<Boolean> callGetCheckByEmailApi = userRepository.findCheckByEmail(email);
        callGetCheckByEmailApi.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean check = response.body();
                    joinPresenter.onSuccessGetCheckByEmail(check);
                } else {
                    joinPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                log(t);
                joinPresenter.onNetworkError(null);
            }
        });
    }

    @Override
    public void setUser() {
        Call<User> callSetUserApi = userRepository.saveUser(user);
        callSetUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    joinPresenter.onSuccessSetUser();
                } else {
                    joinPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                joinPresenter.onNetworkError(null);
            }
        });
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
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
