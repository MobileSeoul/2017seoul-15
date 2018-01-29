package com.stm.main.base.interactor.impl;


import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.main.base.interactor.MainInteractor;
import com.stm.main.base.presenter.MainPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-06-07.
 */

public class MainInteractorImpl implements MainInteractor {
    private MainPresenter mainPresenter;
    private User user;

    private static final Logger logger = LoggerFactory.getLogger(MainInteractorImpl.class);

    public MainInteractorImpl(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
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
