package com.stm.user.edit.account.base.interactor.impl;

import com.stm.common.dao.User;
import com.stm.common.flag.LogFlag;
import com.stm.user.edit.account.base.interactor.EditAccountInteractor;
import com.stm.user.edit.account.base.presenter.EditAccountPresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ㅇㅇ on 2017-08-31.
 */

public class EditAccountInteractorImpl implements EditAccountInteractor {
    private EditAccountPresenter editAccountPresenter;
    private User user;
    private static final Logger logger = LoggerFactory.getLogger(EditAccountInteractorImpl.class);

    public EditAccountInteractorImpl(EditAccountPresenter editAccountPresenter) {
        this.editAccountPresenter = editAccountPresenter;
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
