package com.stm.login.create.joincategory.fragment.merchant.base.interactor.impl;

import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.create.joincategory.fragment.merchant.base.interactor.MerchantInteractor;
import com.stm.login.create.joincategory.fragment.merchant.base.presenter.MerchantPresenter;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.repository.remote.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class MerchantInteractorImpl implements MerchantInteractor {
    private MerchantPresenter merchantPresenter;
    private User user;

    private Market market;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MerchantInteractorImpl.class);


    public MerchantInteractorImpl(MerchantPresenter merchantPresenter) {
        this.merchantPresenter = merchantPresenter;
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
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

    @Override
    public void setUser() {
        Call<User> callSetUserApi = userRepository.saveUser(user);
        callSetUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    merchantPresenter.onSuccessSetUser();
                } else {
                    merchantPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                merchantPresenter.onNetworkError(null);
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
