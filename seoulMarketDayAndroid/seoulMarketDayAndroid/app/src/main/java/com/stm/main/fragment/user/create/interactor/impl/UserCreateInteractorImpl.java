package com.stm.main.fragment.user.create.interactor.impl;

import com.stm.main.fragment.user.create.interactor.UserCreateInteractor;
import com.stm.main.fragment.user.create.presenter.UserCreatePresenter;

/**
 * Created by ㅇㅇ on 2017-07-03.
 */

public class UserCreateInteractorImpl implements UserCreateInteractor{
    private UserCreatePresenter userCreatePresenter;

    public UserCreateInteractorImpl(UserCreatePresenter userCreatePresenter) {
        this.userCreatePresenter = userCreatePresenter;
    }
}
