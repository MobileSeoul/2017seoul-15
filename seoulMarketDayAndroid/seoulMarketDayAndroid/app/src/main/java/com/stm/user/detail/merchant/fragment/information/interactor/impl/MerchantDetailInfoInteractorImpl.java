package com.stm.user.detail.merchant.fragment.information.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.detail.merchant.fragment.information.interactor.MerchantDetailInfoInteractor;
import com.stm.user.detail.merchant.fragment.information.presenter.MerchantDetailInfoPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class MerchantDetailInfoInteractorImpl implements MerchantDetailInfoInteractor {
    private MerchantDetailInfoPresenter merchantDetailInfoPresenter;
    private User storyUser;
    private User user;
    private static final Logger logger = LoggerFactory.getLogger(MerchantDetailInfoInteractorImpl.class);

    public MerchantDetailInfoInteractorImpl(MerchantDetailInfoPresenter merchantDetailInfoPresenter) {
        this.merchantDetailInfoPresenter = merchantDetailInfoPresenter;
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
