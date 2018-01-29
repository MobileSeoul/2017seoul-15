package com.stm.login.create.joincategory.base.interactor.impl;

import com.stm.common.flag.LogFlag;
import com.stm.login.create.joincategory.base.interactor.JoinCategoryInteractor;
import com.stm.login.create.joincategory.base.presenter.JoinCategoryPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-06-20.
 */

public class JoinCategoryInteractorImpl implements JoinCategoryInteractor {
    private JoinCategoryPresenter joinCategoryPresenter;


    private static final Logger logger = LoggerFactory.getLogger(JoinCategoryInteractorImpl.class);

    public JoinCategoryInteractorImpl(JoinCategoryPresenter joinCategoryPresenter) {
        this.joinCategoryPresenter = joinCategoryPresenter;
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
