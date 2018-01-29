package com.stm.user.edit.account.exit.interactor.impl;

import com.stm.common.dao.Exit;
import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.ExitRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.edit.account.exit.interactor.ExitInteractor;
import com.stm.user.edit.account.exit.presenter.ExitPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitInteractorImpl implements ExitInteractor {
    private ExitPresenter exitPresenter;
    private User user;
    private ExitCategory exitCategory;
    private ExitRepository exitRepository;
    private Boolean isExist;
    private static final Logger logger = LoggerFactory.getLogger(ExitInteractorImpl.class);

    public ExitInteractorImpl(ExitPresenter exitPresenter) {
        this.exitPresenter = exitPresenter;
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
    public ExitCategory getExitCategory() {
        return exitCategory;
    }

    @Override
    public void setExitRepository(String accessToken) {
        exitRepository = new NetworkInterceptor(accessToken).getRetrofitForExitRepository().create(ExitRepository.class);
    }

    @Override
    public void setExitCategory(ExitCategory exitCategory) {
        this.exitCategory = exitCategory;
    }

    @Override
    public Boolean getExist() {
        return isExist;
    }

    @Override
    public void setExist(Boolean exist) {
        isExist = exist;
    }

    @Override
    public void setExit(Exit exit) {
        Call<Boolean> callSaveExitApi = exitRepository.saveExit(exit);
        callSaveExitApi.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    isExist = response.body();
                    exitPresenter.onSuccessSetExit(isExist);
                } else {
                    exitPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                log(t);
                exitPresenter.onNetworkError(null);
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
