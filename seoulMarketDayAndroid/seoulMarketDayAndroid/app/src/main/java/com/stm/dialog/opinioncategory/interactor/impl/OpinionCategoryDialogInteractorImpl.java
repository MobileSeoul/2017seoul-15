package com.stm.dialog.opinioncategory.interactor.impl;

import com.stm.common.dao.OpinionCategory;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.opinioncategory.interactor.OpinionCategoryDialogInteractor;
import com.stm.dialog.opinioncategory.presenter.OpinionCategoryDialogPresenter;
import com.stm.repository.remote.OpinionCategoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public class OpinionCategoryDialogInteractorImpl implements OpinionCategoryDialogInteractor {
    private OpinionCategoryDialogPresenter opinionCategoryDialogPresenter;
    private OpinionCategoryRepository opinionCategoryRepository;
    private List<OpinionCategory> opinionCategories;

    private static final Logger logger = LoggerFactory.getLogger(OpinionCategoryDialogInteractorImpl.class);

    public OpinionCategoryDialogInteractorImpl(OpinionCategoryDialogPresenter opinionCategoryDialogPresenter) {
        this.opinionCategoryDialogPresenter = opinionCategoryDialogPresenter;
        this.opinionCategories = new ArrayList<>();
    }
    
    @Override
    public List<OpinionCategory> getOpinionCategories() {
        return opinionCategories;
    }

    @Override
    public void setOpinionCategories(List<OpinionCategory> opinionCategories) {
        this.opinionCategories = opinionCategories;
    }

    @Override
    public void setOpinionCategoryRepository() {
        opinionCategoryRepository = new NetworkInterceptor().getRetrofitForOpinionCategoryRepository().create(OpinionCategoryRepository.class);
    }

    @Override
    public void getAllOpinionCategories() {
        Call<List<OpinionCategory>> callGetOpinionCategoriesApi = opinionCategoryRepository.findOpinionCategoryList();
        callGetOpinionCategoriesApi.enqueue(new Callback<List<OpinionCategory>>() {
            @Override
            public void onResponse(Call<List<OpinionCategory>> call, Response<List<OpinionCategory>> response) {
                if (response.isSuccessful()) {
                    List<OpinionCategory> opinionCategories = response.body();
                    opinionCategoryDialogPresenter.onSuccessGetOpinionCategories(opinionCategories);
                } else {
                    opinionCategoryDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<OpinionCategory>> call, Throwable t) {
                log(t);
                opinionCategoryDialogPresenter.onNetworkError(null);
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
