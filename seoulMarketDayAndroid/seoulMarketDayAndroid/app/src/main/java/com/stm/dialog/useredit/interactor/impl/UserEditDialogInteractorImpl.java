package com.stm.dialog.useredit.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.dialog.useredit.interactor.UserEditDialogInteractor;
import com.stm.dialog.useredit.presenter.UserEditDialogPresenter;
import com.stm.repository.remote.UserRepository;
import com.stm.repository.remote.interceptor.NetworkInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dev-0 on 2017-08-23.
 */

public class UserEditDialogInteractorImpl implements UserEditDialogInteractor {
    private UserEditDialogPresenter userEditDialogPresenter;
    private User user;
    private int editFlag;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserEditDialogInteractorImpl.class);


    public UserEditDialogInteractorImpl(UserEditDialogPresenter userEditDialogPresenter) {
        this.userEditDialogPresenter = userEditDialogPresenter;
    }

    @Override
    public void setUserRepository(String accessToken) {
        userRepository = new NetworkInterceptor(accessToken).getRetrofitForUserRepository().create(UserRepository.class);
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
    public int getEditFlag() {
        return editFlag;
    }

    @Override
    public void setEditFlag(int editFlag) {
        this.editFlag = editFlag;
    }


    @Override
    public void updateUser(User user) {
        long userId = user.getId();
        Call<User> callUpdateUserApi = userRepository.updateUser(userId, user);
        callUpdateUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userEditDialogPresenter.onSuccessUpdateUser(user);
                } else {
                    userEditDialogPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                userEditDialogPresenter.onNetworkError(null);
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
