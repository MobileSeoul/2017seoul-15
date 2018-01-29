package com.stm.login.reset.password.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.reset.password.interactor.ResetPasswordInteractor;
import com.stm.login.reset.password.presenter.ResetPasswordPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-09-05.
 */

public class ResetPasswordInteractorImpl implements ResetPasswordInteractor {
    private ResetPasswordPresenter resetPasswordPresenter;
    private User user;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordInteractorImpl.class);


    public ResetPasswordInteractorImpl(ResetPasswordPresenter resetPasswordPresenter) {
        this.resetPasswordPresenter = resetPasswordPresenter;
    }

    @Override
    public void setUserRepository() {
        userRepository = new NetworkInterceptor().getRetrofitForUserRepository().create(UserRepository.class);
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
    public void updateUserByResettingPassword(User user) {
        Call<ResponseBody> CallUpdateUserByResettingPasswordApi = userRepository.updateUserByResettingPassword(user);
        CallUpdateUserByResettingPasswordApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    resetPasswordPresenter.onSuccessUpdateUserByResettingPassword();
                } else {
                    resetPasswordPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log(t);
                resetPasswordPresenter.onNetworkError(null);
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
