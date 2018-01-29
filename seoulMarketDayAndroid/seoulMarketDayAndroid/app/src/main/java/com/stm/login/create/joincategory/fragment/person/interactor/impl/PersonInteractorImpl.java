package com.stm.login.create.joincategory.fragment.person.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.common.util.ErrorUtil;
import com.stm.login.create.joincategory.fragment.person.interactor.PersonInteractor;
import com.stm.login.create.joincategory.fragment.person.presenter.PersonPresenter;
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

public class PersonInteractorImpl implements PersonInteractor {
    private PersonPresenter personPresenter;
    private User user;

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PersonInteractorImpl.class);

    public PersonInteractorImpl(PersonPresenter personPresenter) {
        this.personPresenter = personPresenter;
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
    public void setUser() {
        Call<User> callSetUserApi = userRepository.saveUser(user);
        callSetUserApi.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    personPresenter.onSuccessSetUser();
                } else {
                    personPresenter.onNetworkError(new ErrorUtil(getClass()).parseError(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                log(t);
                personPresenter.onNetworkError(null);
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
