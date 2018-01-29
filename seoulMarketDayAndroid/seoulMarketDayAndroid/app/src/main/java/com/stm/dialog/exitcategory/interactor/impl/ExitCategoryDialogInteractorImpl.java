package com.stm.dialog.exitcategory.interactor.impl;

import com.stm.common.dao.ExitCategory;
import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.exitcategory.interactor.ExitCategoryDialogInteractor;
import com.stm.dialog.exitcategory.presenter.ExitCategoryDialogPresenter;
import com.stm.repository.remote.ExitCategoryRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class ExitCategoryDialogInteractorImpl implements ExitCategoryDialogInteractor {
    private ExitCategoryDialogPresenter exitCategoryDialogPresenter;
    private User user;
    private List<ExitCategory> exitCategories;
    private ExitCategoryRepository exitCategoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExitCategoryDialogInteractorImpl.class);

    public ExitCategoryDialogInteractorImpl(ExitCategoryDialogPresenter exitCategoryDialogPresenter) {
        this.exitCategoryDialogPresenter = exitCategoryDialogPresenter;
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
    public List<ExitCategory> getExitCategories() {
        return exitCategories;
    }

    @Override
    public void setExitCategories(List<ExitCategory> exitCategories) {
        this.exitCategories = exitCategories;
    }

    @Override
    public void setExitCategoryRepository(String accessToken) {
        this.exitCategoryRepository = new NetworkInterceptor(accessToken).getRetrofitForExitCategoryRepository().create(ExitCategoryRepository.class);
    }

    @Override
    public void getExitCategoryList() {
        Call<List<ExitCategory>> callGetExitCategoryListApi = exitCategoryRepository.findExitCategoryList();
        callGetExitCategoryListApi.enqueue(new Callback<List<ExitCategory>>() {
            @Override
            public void onResponse(Call<List<ExitCategory>> call, Response<List<ExitCategory>> response) {
                if (response.isSuccessful()) {
                    exitCategories = response.body();
                    exitCategoryDialogPresenter.onSuccessGetExitCategoryList(exitCategories);
                } else {
                    exitCategoryDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<List<ExitCategory>> call, Throwable t) {
                log(t);
                exitCategoryDialogPresenter.onNetworkError(null);
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
