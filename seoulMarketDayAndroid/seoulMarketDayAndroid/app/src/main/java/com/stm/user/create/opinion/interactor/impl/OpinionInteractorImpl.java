package com.stm.user.create.opinion.interactor.impl;

import com.stm.common.dao.Opinion;
import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.repository.remote.OpinionRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;
import com.stm.user.create.opinion.interactor.OpinionInteractor;
import com.stm.user.create.opinion.presenter.OpinionPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionInteractorImpl implements OpinionInteractor {
    private OpinionPresenter opinionPresenter;
    private User user;
    private OpinionRepository opinionRepository;
    private OpinionCategory opinionCategory;

    private static final Logger logger = LoggerFactory.getLogger(OpinionInteractorImpl.class);


    public OpinionInteractorImpl(OpinionPresenter opinionPresenter) {
        this.opinionPresenter = opinionPresenter;
    }

    @Override
    public void setOpinionRepository(String accessToken) {
        opinionRepository = new NetworkInterceptor(accessToken).getRetrofitForOpinionRepository().create(OpinionRepository.class);
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
    public OpinionCategory getOpinionCategory() {
        return opinionCategory;
    }

    @Override
    public void setOpinionCategory(OpinionCategory opinionCategory) {
        this.opinionCategory = opinionCategory;
    }

    @Override
    public void insertOpinion(Opinion opinion) {
        Call<ResponseBody> callInsertOpinionApi = opinionRepository.saveOpinion(opinion);
        callInsertOpinionApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    opinionPresenter.onSuccessInsertOpinion();
                } else {
                    opinionPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                opinionPresenter.onNetworkError(null);

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
