package com.stm.main.fragment.main.search.base.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.main.fragment.main.search.base.interactor.SearchInteractor;
import com.stm.main.fragment.main.search.base.presenter.SearchPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchInteractorImpl implements SearchInteractor {
    private SearchPresenter searchPresenter;
    private User user;
    private static final Logger logger = LoggerFactory.getLogger(SearchInteractorImpl.class);

    public SearchInteractorImpl(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
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
